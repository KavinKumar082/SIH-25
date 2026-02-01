package com.example.backend.repository.customer;

import com.example.backend.domain.entity.customer.AccountAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountAddressRepository
        extends JpaRepository<AccountAddress, Long> {

    @Query("""
        select a.city
        from AccountAddress a
        join a.account acc
        join acc.customer c
        where c.customerId = :customerId
        order by a.addressId asc
    """)
    Optional<String> findCityByCustomerId(Long customerId);

    Optional<AccountAddress> findFirstByAccountAccountId(Long accountId);

    
}
