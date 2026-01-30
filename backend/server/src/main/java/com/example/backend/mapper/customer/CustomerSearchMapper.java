package com.example.backend.mapper.customer;

import com.example.backend.domain.entity.customer.Customer;
import com.example.backend.dto.response.customer.CustomerSearchResponse;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

public final class CustomerSearchMapper {

    private CustomerSearchMapper() {
        // utility class
    }

    public static CustomerSearchResponse toResponse(
            Customer customer,
            @Nullable Integer priorityScore,
            @Nullable BigDecimal outstandingAmount,
            String slaStatus,
            @Nullable String city
    ) {
        return new CustomerSearchResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                priorityScore,
                outstandingAmount,
                slaStatus,
                city
        );
    }
}
