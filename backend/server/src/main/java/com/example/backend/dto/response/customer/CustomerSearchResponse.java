package com.example.backend.dto.response.customer;

import java.math.BigDecimal;

public class CustomerSearchResponse {

    private Long customerId;
    private String customerName;
    private Integer priorityScore;
    private BigDecimal outstandingAmount;
    private String slaStatus;
    private String city;

    public CustomerSearchResponse(
            Long customerId,
            String customerName,
            Integer priorityScore,
            BigDecimal outstandingAmount,
            String slaStatus,
            String city
    ) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.priorityScore = priorityScore;
        this.outstandingAmount = outstandingAmount;
        this.slaStatus = slaStatus;
        this.city = city;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Integer getPriorityScore() {
        return priorityScore;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public String getSlaStatus() {
        return slaStatus;
    }

    public String getCity() {
        return city;
    }
}
