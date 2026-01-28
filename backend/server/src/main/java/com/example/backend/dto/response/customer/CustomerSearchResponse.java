package com.example.backend.dto.response.customer;

public class CustomerSearchResponse {

    private final Long customerId;
    private final String customerName;
    private final String city;

    public CustomerSearchResponse(
            Long customerId,
            String customerName,
            String city
    ) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.city = city;
    }

    // ---------- Getters only (NO setters) ----------

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCity() {
        return city;
    }
}
