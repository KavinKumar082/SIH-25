package com.example.backend.dto.response.dashboard;

import java.math.BigDecimal;

public class RecoveryTrendDTO {

    private String period;   // "Jan", "Feb"
    private BigDecimal amount;

    public RecoveryTrendDTO(String period, BigDecimal amount) {
        this.period = period;
        this.amount = amount;
    }

    public String getPeriod() {
        return period;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
