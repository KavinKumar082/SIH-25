package com.example.backend.dto.response.dca;

import com.example.backend.domain.entity.dca.CasePriorityScore;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
public class PriorityCasePageResponse {

    private Long caseId;
    private Integer priorityScore;
    private OffsetDateTime computedAt;

    public static PriorityCasePageResponse fromEntity(CasePriorityScore e) {
        PriorityCasePageResponse r = new PriorityCasePageResponse();

        // ✅ CORRECT WAY
        r.caseId = e.getDcaCase().getCaseId();

        r.priorityScore = e.getScore();
        r.computedAt = e.getEffectiveDate().atOffset(ZoneOffset.UTC);

        return r;
    }
}
