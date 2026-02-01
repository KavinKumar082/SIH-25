package com.example.backend.repository.sla;

import com.example.backend.domain.entity.sla.CaseSlaRule;
import com.example.backend.domain.enums.sla.CollectionStage;
import com.example.backend.domain.enums.sla.Severity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaseSlaRuleRepository
        extends JpaRepository<CaseSlaRule, Long> {

    Optional<CaseSlaRule> findByCollectionStageAndSeverity(
            CollectionStage collectionStage,
            Severity severity
    );
}
