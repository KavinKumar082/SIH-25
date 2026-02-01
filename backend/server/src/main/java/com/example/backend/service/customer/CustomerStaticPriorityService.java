package com.example.backend.service.customer;

import com.example.backend.domain.entity.customer.Customer;
import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.entity.customer.CustomerPriorityScore;
import com.example.backend.repository.customer.CustomerAccountRepository;
import com.example.backend.repository.customer.CustomerPriorityScoreRepository;
import com.example.backend.repository.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerStaticPriorityService {

    private final CustomerRepository customerRepository;
    private final CustomerAccountRepository accountRepository;
    private final CustomerPriorityScoreRepository priorityScoreRepository;

    public CustomerStaticPriorityService(
            CustomerRepository customerRepository,
            CustomerAccountRepository accountRepository,
            CustomerPriorityScoreRepository priorityScoreRepository
    ) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.priorityScoreRepository = priorityScoreRepository;
    }

    @Transactional
    public void computeForCustomer(Long customerId) {

        Objects.requireNonNull(customerId, "customerId must not be null");

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found: " + customerId)
                );

        List<CustomerAccount> accounts =
                accountRepository.findByCustomerCustomerId(customerId);

        if (accounts.isEmpty()) return;

        StaticPriorityCalculator.Result best =
                accounts.stream()
                        .map(StaticPriorityCalculator::calculate)
                        .max((a, b) -> Integer.compare(a.score(), b.score()))
                        .orElseThrow();

        CustomerPriorityScore cps = new CustomerPriorityScore();
        cps.setCustomer(customer);
        cps.setScore(best.score());
        cps.setReason(best.reason());

        priorityScoreRepository.save(cps);
    }

    @Transactional
public void recomputeForAllActiveCustomers() {
    computeForAll(Pageable.unpaged());
}


    @Transactional
    public void computeForAll(Pageable pageable) {

        Objects.requireNonNull(pageable, "pageable must not be null");

        Page<Customer> customers = customerRepository.findAll(pageable);

        customers.forEach(customer -> {
            try {
                computeForCustomer(customer.getCustomerId());
            } catch (Exception ignored) {
                // isolate bad data
            }
        });
    }
}
