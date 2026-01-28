package com.example.backend.dto.response.test;

import java.math.BigDecimal;

public record CommissionUpdateResponse(
        Long commissionId,
        Before before,
        After after
) {

    public record Before(BigDecimal commissionAmount) {}

    public record After(BigDecimal commissionAmount) {}
}
