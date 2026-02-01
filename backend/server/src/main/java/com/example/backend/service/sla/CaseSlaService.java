package com.example.backend.service.sla;

import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.entity.dca.DcaCase;
import com.example.backend.domain.entity.sla.CaseSlaRule;
import com.example.backend.domain.enums.sla.CollectionStage;
import com.example.backend.domain.enums.sla.Severity;
import com.example.backend.repository.sla.CaseSlaRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CaseSlaService {

    private final CaseSlaRuleRepository caseSlaRuleRepository;

    public void generateCaseSla(DcaCase dcaCase, CustomerAccount account) {

        // 🔁 CONVERT customer → sla enum
        CollectionStage stage =
                CollectionStage.valueOf(account.getCollectionStage().name());

        Severity severity = deriveSeverity(account);

        CaseSlaRule rule =
                caseSlaRuleRepository
                        .findByCollectionStageAndSeverity(stage, severity)
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "No Case SLA rule for stage=" + stage +
                                        ", severity=" + severity
                                )
                        );

        dcaCase.setCaseSlaId(rule.getCaseSlaId());
        dcaCase.setCaseSlaDueDate(
                LocalDate.now().plusDays(rule.getMaxResolutionDays())
        );
    }

    private Severity deriveSeverity(CustomerAccount account) {
        return switch (account.getRiskSegment()) {
            case High -> Severity.High;
            case Medium -> Severity.Medium;
            default -> Severity.Low;
        };
    }
}
