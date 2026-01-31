package com.example.backend.controller.dca;

import com.example.backend.adapter.dca.PriorityAdapter;
import com.example.backend.dto.response.dca.PriorityCasePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/priority")
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityAdapter priorityAdapter;

    @PostMapping("/recompute")
    public void recomputePriority() {
        priorityAdapter.triggerRecompute();
    }

    @GetMapping("/cases")
    public Page<PriorityCasePageResponse> getPrioritizedCases(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "score") String sortField,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) Integer minPriority
    ) {
        return priorityAdapter.fetchPrioritizedCases(
                page, size, sortField, sortOrder, minPriority
        );
    }
}
