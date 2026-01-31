package com.example.backend.repository.dca;

import com.example.backend.domain.entity.dca.CasePriorityScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CasePriorityScoreRepository
        extends JpaRepository<CasePriorityScore, Long> {

    @Query("""
        SELECT cps
        FROM CasePriorityScore cps
        WHERE (:minPriority IS NULL OR cps.score >= :minPriority)
    """)
    Page<CasePriorityScore> findForFn(Integer minPriority, Pageable pageable);
}
