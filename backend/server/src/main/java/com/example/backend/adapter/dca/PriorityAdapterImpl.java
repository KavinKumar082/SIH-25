package com.example.backend.adapter.dca;

import com.example.backend.domain.entity.dca.CasePriorityScore;
import com.example.backend.dto.response.dca.PriorityCasePageResponse;
import com.example.backend.repository.dca.CasePriorityScoreRepository;
import com.example.backend.service.dca.PriorityEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class PriorityAdapterImpl implements PriorityAdapter {

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("score", "caseId", "effectiveDate");

    private final PriorityEngineService priorityEngineService;
    private final CasePriorityScoreRepository casePriorityScoreRepository;

    @Override
    public void triggerRecompute() {
        priorityEngineService.recomputeDynamicPriorities();
    }

    @Override
    public Page<PriorityCasePageResponse> fetchPrioritizedCases(
            int page,
            int size,
            String sortField,
            String sortOrder,
            Integer minPriority
    ) {

        if (!ALLOWED_SORT_FIELDS.contains(sortField)) {
            sortField = "score";
        }

        Sort.Direction direction =
                "asc".equalsIgnoreCase(sortOrder)
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<CasePriorityScore> result =
                casePriorityScoreRepository.findForFn(minPriority, pageable);

        return result.map(PriorityCasePageResponse::fromEntity);
    }
}
