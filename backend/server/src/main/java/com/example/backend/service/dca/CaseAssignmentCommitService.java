package com.example.backend.service.dca;

import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.entity.customer.CustomerPriorityScore;
import com.example.backend.domain.entity.dca.CasePriorityScore;
import com.example.backend.domain.entity.dca.Dca;
import com.example.backend.domain.entity.dca.DcaCase;
import com.example.backend.domain.enums.dca.CaseStatus;
import com.example.backend.dto.request.dca.CaseAssignmentCommitRequest;
import com.example.backend.repository.customer.CustomerAccountRepository;
import com.example.backend.repository.customer.CustomerPriorityScoreRepository;
import com.example.backend.repository.dca.CasePriorityScoreRepository;
import com.example.backend.repository.dca.DcaCaseRepository;
import com.example.backend.repository.dca.DcaRepository;
import com.example.backend.service.sla.CaseSlaService;
import com.example.backend.service.sla.SopActionSlaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CaseAssignmentCommitService {

    private final DcaRepository dcaRepository;
    private final DcaCaseRepository dcaCaseRepository;
    private final CasePriorityScoreRepository casePriorityScoreRepository;
    private final CustomerAccountRepository customerAccountRepository;
    private final CustomerPriorityScoreRepository customerPriorityScoreRepository;

    private final CaseSlaService caseSlaService;
    private final SopActionSlaService sopActionSlaService;
    private final PriorityEngineService priorityEngineService;

    @Transactional("dcaTransactionManager")
    public void commit(CaseAssignmentCommitRequest request) {

        if (request == null || request.batches() == null || request.batches().isEmpty()) {
            throw new IllegalArgumentException("No DCA batches provided");
        }

        for (CaseAssignmentCommitRequest.DcaCaseBatch batch : request.batches()) {

            Dca dca = dcaRepository.findById(batch.dcaId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Invalid DCA ID: " + batch.dcaId())
                    );

            for (Long accountId : batch.accountIds()) {

                if (dcaCaseRepository.existsByAccountId(accountId)) {
                    throw new IllegalStateException("Account already assigned: " + accountId);
                }

                CustomerAccount account =
                        customerAccountRepository.findById(accountId)
                                .orElseThrow(() ->
                                        new IllegalArgumentException("Invalid accountId: " + accountId)
                                );

                // 1️⃣ CREATE DCA_CASE
                DcaCase dcaCase = new DcaCase();
                dcaCase.setAccountId(accountId);
                dcaCase.setDca(dca);
                dcaCase.setCaseStatus(CaseStatus.Open);

                dcaCaseRepository.save(dcaCase);

                // 2️⃣ GENERATE CASE SLA
                caseSlaService.generateCaseSla(dcaCase, account);

                // 3️⃣ GENERATE SOP ACTION SLAs
                sopActionSlaService.generateActionSlas(dcaCase, account);

                // 4️⃣ INSERT INITIAL CASE_PRIORITY_SCORE (MANDATORY)
                CustomerPriorityScore staticPriority =
                        customerPriorityScoreRepository
                                .findTopByCustomerCustomerIdOrderByEffectiveDateDesc(
                                        account.getCustomer().getCustomerId()
                                )
                                .orElseThrow(() ->
                                        new IllegalStateException(
                                                "Static priority missing for customerId=" +
                                                        account.getCustomer().getCustomerId()
                                        )
                                );

                CasePriorityScore cps = new CasePriorityScore();
                cps.setDcaCase(dcaCase);
                cps.setScore(staticPriority.getScore()); // base score
                cps.setReason(
                        java.util.Map.of(
                                "source", "commit",
                                "static_priority", staticPriority.getScore()
                        )
                );

                casePriorityScoreRepository.save(cps);
            }
        }

        // 5️⃣ NOW SAFE TO RUN DYNAMIC PRIORITY
        priorityEngineService.recomputeDynamicPriorities();
    }
}
