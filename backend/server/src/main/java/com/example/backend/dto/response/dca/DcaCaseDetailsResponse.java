package com.example.backend.dto.response.dca;

import java.time.LocalDate;

public record DcaCaseDetailsResponse(
        Long caseId,
        Long accountId,
        String customerName,
        String priorityLevel,
        LocalDate caseSlaDueDate,
        String callStatus,
        String emailStatus,
        String visitStatus,
        String caseStatus
) {}
