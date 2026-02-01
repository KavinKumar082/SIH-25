package com.example.backend.controller.customer;

import com.example.backend.service.customer.CustomerStaticPriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/customers")
@RequiredArgsConstructor
public class CustomerStaticPriorityController {

    private final CustomerStaticPriorityService priorityService;

    @PostMapping("/{customerId}/static-priority/compute")
    public void computeForCustomer(@PathVariable Long customerId) {
        priorityService.computeForCustomer(customerId);
    }

    @PostMapping("/static-priority/compute")
    public void computeForAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        priorityService.computeForAll(pageable);
    }
}
