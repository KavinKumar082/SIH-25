package com.example.backend.dto.request.dca;

import java.util.List;

public record CaseAssignmentCommitRequest(
        List<DcaCaseBatch> batches
) {
    public record DcaCaseBatch(
            Long dcaId,
            List<Long> accountIds
    ) {}
}
