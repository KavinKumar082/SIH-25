package com.example.backend.mapper.customer;

import com.example.backend.domain.entity.customer.Customer;
import com.example.backend.dto.response.customer.CustomerSearchResponse;


public class CustomerSearchMapper {

    private CustomerSearchMapper() {}

    public static CustomerSearchResponse toResponse(
            Customer customer,
            String city
    ) {
        return new CustomerSearchResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                city
        );
    }
}
