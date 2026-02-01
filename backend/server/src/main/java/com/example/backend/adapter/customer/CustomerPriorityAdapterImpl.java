package com.example.backend.adapter.customer;

import com.example.backend.service.customer.CustomerStaticPriorityService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CustomerPriorityAdapterImpl implements CustomerPriorityAdapter {

    private final CustomerStaticPriorityService service;

    public CustomerPriorityAdapterImpl(CustomerStaticPriorityService service) {
        this.service = service;
    }

    @Override
    public void computeForCustomer(Long customerId) {
        service.computeForCustomer(customerId);
    }

    @Override
    public void computeForAll(Pageable pageable) {
        service.computeForAll(pageable);
    }
}
