package com.example.backend.adapter.customer;

import org.springframework.data.domain.Pageable;

public interface CustomerPriorityAdapter {

    void computeForCustomer(Long customerId);

    void computeForAll(Pageable pageable);
}
