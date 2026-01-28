package com.example.backend.controller.customer;

import com.example.backend.dto.request.customer.SearchFilterRequest;
import com.example.backend.dto.response.customer.CustomerSearchResponse;
import com.example.backend.service.customer.CustomerSearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerSearchController {

    private final CustomerSearchService customerSearchService;

    public CustomerSearchController(CustomerSearchService customerSearchService) {
        this.customerSearchService = customerSearchService;
    }

    @PostMapping("/search")
    public Page<CustomerSearchResponse> searchCustomers(
            @RequestBody SearchFilterRequest request
    ) {
        return customerSearchService.search(request);
    }
}
