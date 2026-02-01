package com.example.backend.dto.response.customer;

import java.math.BigDecimal;

public record AssignCasePreviewResponse(
        Long customerId,
        String customerName,
        String city,
        String accountRegion,
        BigDecimal outstandingAmount,
        String ageingBucket,
        Integer staticPriorityScore,
        boolean assigned
        //Long recommendedDcaId,
        //String recommendedDcaName
) {}
