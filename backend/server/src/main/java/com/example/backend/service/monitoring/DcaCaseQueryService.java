package com.example.backend.service.monitoring;

import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.entity.dca.DcaActionLog;
import com.example.backend.domain.entity.dca.DcaCase;
import com.example.backend.domain.enums.dca.CaseStatus;
import com.example.backend.dto.response.dca.DcaCaseDetailsResponse;
import com.example.backend.repository.customer.CustomerAccountRepository;
import com.example.backend.repository.dca.CasePriorityScoreRepository;
import com.example.backend.repository.dca.DcaActionLogRepository;
import com.example.backend.repository.dca.DcaCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DcaCaseQueryService {

    private final DcaCaseRepository dcaCaseRepository;
    private final CustomerAccountRepository customerAccountRepository;
    private final CasePriorityScoreRepository casePriorityScoreRepository;
    private final DcaActionLogRepository dcaActionLogRepository;

    // SOP mapping based on YOUR SOP_Rule seed data
    private static final Long[] CALL_SOPS  = {1L, 2L};
    private static final Long[] EMAIL_SOPS = {3L, 4L};
    private static final Long[] VISIT_SOPS = {5L, 6L};

    public List<DcaCaseDetailsResponse> fetchCases(Long dcaId) {

        return dcaCaseRepository.findByDca_DcaId(dcaId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DcaCaseDetailsResponse mapToResponse(DcaCase dcaCase) {

        CustomerAccount account =
                customerAccountRepository.findById(dcaCase.getAccountId())
                        .orElseThrow();

        // ✅ Priority from Case_Priority_Score (latest only)
        String priorityLevel =
                casePriorityScoreRepository
                        .findTopByDcaCase_CaseIdOrderByEffectiveDateDesc(
                                dcaCase.getCaseId()
                        )
                        .map(cps -> mapPriority(cps.getScore()))
                        .orElse("Low");

        // ✅ CLOSED CASE → do NOT evaluate SOP / SLA
        if (dcaCase.getCaseStatus() == CaseStatus.Closed) {
            return new DcaCaseDetailsResponse(
                    dcaCase.getCaseId(),
                    dcaCase.getAccountId(),
                    account.getCustomer().getCustomerName(),
                    priorityLevel,
                    dcaCase.getCaseSlaDueDate(),
                    "-",    // call
                    "-",    // email
                    "-",    // visit
                    dcaCase.getCaseStatus().name()
            );
        }

        // ✅ OPEN CASE → evaluate actions
        List<DcaActionLog> actions =
                dcaActionLogRepository.findByDcaCase_CaseId(
                        dcaCase.getCaseId()
                );

        String callStatus  = hasAction(actions, CALL_SOPS)  ? "Done" : "Pending";
        String emailStatus = hasAction(actions, EMAIL_SOPS) ? "Done" : "Pending";
        String visitStatus = hasAction(actions, VISIT_SOPS) ? "Done" : "Pending";

        return new DcaCaseDetailsResponse(
                dcaCase.getCaseId(),
                dcaCase.getAccountId(),
                account.getCustomer().getCustomerName(),
                priorityLevel,
                dcaCase.getCaseSlaDueDate(),
                callStatus,
                emailStatus,
                visitStatus,
                dcaCase.getCaseStatus().name()
        );
    }

    // ===============================
    // Helpers
    // ===============================

    private String mapPriority(Integer score) {
        if (score >= 80) return "High";
        if (score >= 50) return "Medium";
        return "Low";
    }

    private boolean hasAction(List<DcaActionLog> logs, Long[] sopIds) {
        for (DcaActionLog log : logs) {
            for (Long sopId : sopIds) {
                if (sopId.equals(log.getSopId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
