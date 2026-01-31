package com.example.backend.adapter.dca;

import com.example.backend.dto.response.dca.PriorityCasePageResponse;
import org.springframework.data.domain.Page;

public interface PriorityAdapter {

    void triggerRecompute();

    Page<PriorityCasePageResponse> fetchPrioritizedCases(
            int page,
            int size,
            String sortField,
            String sortOrder,
            Integer minPriority
    );
}
