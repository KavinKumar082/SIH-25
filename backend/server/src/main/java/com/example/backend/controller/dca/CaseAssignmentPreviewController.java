package com.example.backend.controller.dca;

import com.example.backend.service.dca.CaseAssignmentPreviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dca/assignments")
@RequiredArgsConstructor
public class CaseAssignmentPreviewController {

    private final CaseAssignmentPreviewService previewService;

    /**
     * PREVIEW assignment (NO DB writes)
     */
    @PostMapping("/preview")
    public ResponseEntity<?> previewAssignment() {
        return ResponseEntity.ok(previewService.fetchPreview());
    }
}
