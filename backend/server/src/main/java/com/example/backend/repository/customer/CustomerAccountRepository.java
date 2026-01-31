    package com.example.backend.repository.customer;

    import com.example.backend.domain.entity.customer.CustomerAccount;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;

    import java.math.BigDecimal;
    import java.util.Optional;

    public interface CustomerAccountRepository
            extends JpaRepository<CustomerAccount, Long> {

        @Query("""
            select sum(acc.outstandingAmount)
            from CustomerAccount acc
            where acc.customer.customerId = :customerId
        """)
        Optional<BigDecimal> totalOutstandingByCustomerId(Long customerId);
    }
