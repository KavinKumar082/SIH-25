package com.example.backend.controller.dca;

import com.example.backend.service.dca.PriorityEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dca/priority-engine")
@RequiredArgsConstructor
public class PriorityEngineController {

    private final PriorityEngineService priorityEngineService;

    /**
     * Manual trigger for dynamic priority recompute
     * (Scheduler also runs this every 6 hrs)
     */
    @PostMapping("/recompute")
    public ResponseEntity<String> recomputeDynamicPriority() {

        priorityEngineService.recomputeDynamicPriorities();

        return ResponseEntity.ok("Dynamic priority recomputation triggered");
    }
}
