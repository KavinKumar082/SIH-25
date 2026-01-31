package com.example.backend.repository.customer;

import com.example.backend.domain.entity.customer.CustomerPriorityScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerPriorityScoreRepository
        extends JpaRepository<CustomerPriorityScore, Long> {

    // ✅ existing method (DO NOT TOUCH)
    @Query("""
        select cps.score
        from CustomerPriorityScore cps
        where cps.customer.customerId = :customerId
        order by cps.effectiveDate desc
    """)
    Optional<Integer> findLatestScoreByCustomerId(Long customerId);

    // ✅ NEW method (for Priority Engine only)
    @Query("""
        select cps
        from CustomerPriorityScore cps
        where cps.customer.customerId = :customerId
        order by cps.effectiveDate desc
    """)
    Optional<CustomerPriorityScore> findLatestEntityByCustomerId(Long customerId);
}
