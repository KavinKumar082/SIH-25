package com.example.backend.controller.customer;

import com.example.backend.service.customer.CustomerStaticPriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/static-priority")
@RequiredArgsConstructor
public class CustomerPriorityController {

    private final CustomerStaticPriorityService customerStaticPriorityService;

    /**
     * Admin trigger – recompute static priority for ALL customers
     */
    @PostMapping("/recompute")
    public ResponseEntity<String> recomputeStaticPriority() {
        customerStaticPriorityService.recomputeForAllActiveCustomers();
        return ResponseEntity.ok("Static priority recomputation triggered");
    }
}
