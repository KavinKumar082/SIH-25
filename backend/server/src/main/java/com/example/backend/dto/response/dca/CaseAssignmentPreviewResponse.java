package com.example.backend.dto.response.dca;

/**
 * Sent to FN (Field Navigator / FedEx user)
 * Preview-only recommendation
 */
public record CaseAssignmentPreviewResponse(
        Long accountId,
        Integer staticPriorityScore,
        Long recommendedDcaId,
        String recommendedDcaName
) {}
