package com.example.backend.controller.dca;

import com.example.backend.dto.response.dca.CaseAssignmentPreviewResponse;
import com.example.backend.service.dca.CaseAssignmentPreviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases/assignments")
@RequiredArgsConstructor
public class AssignCaseRecommendationController {

    private final CaseAssignmentPreviewService previewService;

    @GetMapping("/recommendations")
    public List<CaseAssignmentPreviewResponse> getRecommendations() {
        return previewService.fetchRecommendations();
    }
}
