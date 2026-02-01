package com.example.backend.service.dca;

import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.entity.customer.CustomerPriorityScore;
import com.example.backend.domain.entity.dca.CasePriorityScore;
import com.example.backend.domain.entity.dca.DcaActionLog;
import com.example.backend.domain.entity.dca.DcaCase;
import com.example.backend.repository.customer.CustomerAccountRepository;
import com.example.backend.repository.customer.CustomerPriorityScoreRepository;
import com.example.backend.repository.dca.CasePriorityScoreRepository;
import com.example.backend.repository.dca.DcaActionLogRepository;
import com.example.backend.repository.dca.DcaCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PriorityEngineServiceImpl implements PriorityEngineService {

    private final DcaCaseRepository dcaCaseRepository;
    private final CustomerAccountRepository customerAccountRepository;
    private final CustomerPriorityScoreRepository customerPriorityScoreRepository;
    private final DcaActionLogRepository dcaActionLogRepository;
    private final CasePriorityScoreRepository casePriorityScoreRepository;

    @Override
    public void recomputeDynamicPriorities() {

        List<DcaCase> openCases = dcaCaseRepository.findOpenCases();
        LocalDate today = LocalDate.now();

        for (DcaCase dcaCase : openCases) {

            // ===============================
            // NULL-SAFETY GUARD (MANDATORY)
            // ===============================
            Long accountId = dcaCase.getAccountId();
            if (accountId == null) {
                continue;
            }

            CustomerAccount account =
                    customerAccountRepository.findById(accountId).orElse(null);
            if (account == null) {
                continue;
            }

            CustomerPriorityScore staticPriority =
                    customerPriorityScoreRepository
                            .findTopByCustomerCustomerIdOrderByEffectiveDateDesc(
                                    account.getCustomer().getCustomerId()
                            )
                            .orElse(null);

            if (staticPriority == null) {
                continue;
            }
            // ===============================

            int staticScore = staticPriority.getScore();
            int caseSlaPressure = calculateCaseSlaPressure(dcaCase, today);
            int actionPressure = calculateActionPressure(dcaCase.getCaseId());
            int stagePressure =
                    calculateStagePressure(account.getCollectionStage().name());

            int finalScore =
                    staticScore + caseSlaPressure + actionPressure + stagePressure;

            CasePriorityScore cps =
                    casePriorityScoreRepository
                            .findByCaseId(dcaCase.getCaseId())
                            .orElseThrow(() ->
                                    new IllegalStateException(
                                            "CasePriorityScore missing for caseId="
                                                    + dcaCase.getCaseId()
                                    )
                            );

            cps.setScore(finalScore);
            cps.setReason(
                    buildReason(
                            staticScore,
                            caseSlaPressure,
                            actionPressure,
                            stagePressure,
                            account.getCollectionStage().name()
                    )
            );

            casePriorityScoreRepository.save(cps); // UPDATE ONLY
        }
    }

    // ---------- helpers ----------

    private int calculateCaseSlaPressure(DcaCase dcaCase, LocalDate today) {
        if (dcaCase.getCaseSlaDueDate() == null) return 0;

        long days = today.until(dcaCase.getCaseSlaDueDate()).getDays();

        if (days > 5) return 0;
        if (days >= 3) return 5;
        if (days >= 1) return 10;
        return 15;
    }

    private int calculateActionPressure(Long caseId) {
        List<DcaActionLog> breaches =
                dcaActionLogRepository.findBreachedActionsByCaseId(caseId);

        if (breaches.isEmpty()) return 0;
        if (breaches.size() == 1) return 5;
        return 10;
    }

    private int calculateStagePressure(String stage) {
        return switch (stage) {
            case "Active" -> 5;
            case "Legal" -> 10;
            default -> 0;
        };
    }

    private Map<String, Object> buildReason(
            int staticPriority,
            int caseSla,
            int actionSla,
            int stageSla,
            String stage
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("static_priority", staticPriority);
        map.put("case_sla_pressure", caseSla);
        map.put("action_sla_pressure", actionSla);
        map.put("collection_stage", stage);
        map.put("stage_pressure", stageSla);
        map.put("computed_at", LocalDateTime.now().toString());
        return map;
    }
}
