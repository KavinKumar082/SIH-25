package com.example.backend.repository.dca;

import com.example.backend.domain.entity.dca.CasePriorityScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CasePriorityScoreRepository
        extends JpaRepository<CasePriorityScore, Long> {

    // ===============================
    // USED BY FN / REPORTING (EXISTING)
    // ===============================
    @Query("""
        SELECT cps
        FROM CasePriorityScore cps
        WHERE (:minPriority IS NULL OR cps.score >= :minPriority)
    """)
    Page<CasePriorityScore> findForFn(Integer minPriority, Pageable pageable);

    // ===============================
    // USED BY DYNAMIC PRIORITY ENGINE
    // UPDATE-ONLY GUARANTEE
    // ===============================
    @Query("""
        SELECT cps
        FROM CasePriorityScore cps
        WHERE cps.dcaCase.caseId = :caseId
    """)
    Optional<CasePriorityScore> findByCaseId(Long caseId);
}
