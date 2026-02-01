package com.example.backend.controller.customer;

import com.example.backend.dto.response.customer.AssignCasePreviewResponse;
import com.example.backend.service.customer.AssignCasePreviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/assignments")
@RequiredArgsConstructor
public class AssignCasePreviewController {

    private final AssignCasePreviewService assignCasePreviewService;

    @GetMapping("/preview")
    public List<AssignCasePreviewResponse> previewAssignableCases() {
        return assignCasePreviewService.fetchPreview();
    }
}
