package com.example.backend.dto.response.dca;

public record DcaMetricsResponse(
        Long dcaId,
        String dcaName,
        long totalCases,
        long openCases,
        long closedCases,
        int recoveryPercentage
) {}
