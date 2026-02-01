package com.example.backend.repository.customer;

import com.example.backend.domain.entity.customer.Customer;
import com.example.backend.domain.enums.customer.CustomerStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
                JpaSpecificationExecutor<Customer> {

                        List<Customer> findByStatus(CustomerStatus status);
}
