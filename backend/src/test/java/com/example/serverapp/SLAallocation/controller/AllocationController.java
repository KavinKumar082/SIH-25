package com.example.serverapp.allocation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.serverapp.allocation.service.AllocationService;

@RestController
@RequestMapping("/api/allocation")
public class AllocationController {

    private final AllocationService service;

    public AllocationController(AllocationService service) {
        this.service = service;
    }

    @PostMapping("/apply-sla")
    public String applySla() {
        service.applyAllocationAndSla();
        return "Allocated date and SLA due date updated successfully";
    }
}
