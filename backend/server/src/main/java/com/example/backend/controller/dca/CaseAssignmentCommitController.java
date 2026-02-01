package com.example.backend.controller.dca;

import com.example.backend.dto.request.dca.CaseAssignmentCommitRequest;
import com.example.backend.service.dca.CaseAssignmentCommitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dca/assignments")
@RequiredArgsConstructor
public class CaseAssignmentCommitController {

    private final CaseAssignmentCommitService commitService;

    @PostMapping("/commit")
    public ResponseEntity<String> commitAssignment(
            @RequestBody CaseAssignmentCommitRequest request
    ) {
        commitService.commit(request);
        return ResponseEntity.ok("Cases assigned successfully");
    }
}
