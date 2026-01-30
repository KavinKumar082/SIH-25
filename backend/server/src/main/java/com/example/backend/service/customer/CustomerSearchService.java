package com.example.backend.service.customer;

import com.example.backend.domain.entity.customer.Customer;
import com.example.backend.dto.request.customer.SearchFilterRequest;
import com.example.backend.dto.response.customer.CustomerSearchResponse;
import com.example.backend.mapper.customer.CustomerSearchMapper;
import com.example.backend.repository.customer.*;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class CustomerSearchService {

    private final CustomerRepository customerRepository;
    private final AccountAddressRepository addressRepository;
    private final CustomerAccountRepository accountRepository;
    private final CustomerPriorityScoreRepository priorityScoreRepository;

    // 🔒 Allowed sort fields (VERY IMPORTANT)
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "customerId",
            "customerName"
    );

    public CustomerSearchService(
            CustomerRepository customerRepository,
            AccountAddressRepository addressRepository,
            CustomerAccountRepository accountRepository,
            CustomerPriorityScoreRepository priorityScoreRepository
    ) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.accountRepository = accountRepository;
        this.priorityScoreRepository = priorityScoreRepository;
    }

    @Transactional(readOnly = true)
    public Page<CustomerSearchResponse> search(SearchFilterRequest request) {

        // ---------- SORT NORMALIZATION (INDUSTRY GRADE) ----------
        String sortField = request.getSortField();
        String sortOrder = request.getSortOrder();

        if (sortField == null || sortField.isBlank() || !ALLOWED_SORT_FIELDS.contains(sortField)) {
            sortField = "customerId"; // safe default
        }

        Sort.Direction direction;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC; // default
        }

        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getPageSize(),
                Sort.by(direction, sortField)
        );

        // ---------- SEARCH ----------
        Page<Customer> page = customerRepository.findAll(
                CustomerSearchSpecification.search(
                        request.getSearchTerm(),
                        request.getCity()
                ),
                pageable
        );

        // ---------- MAPPING ----------
        return page.map(customer -> {

            Long customerId = customer.getCustomerId();

            String city = addressRepository
                    .findCityByCustomerId(customerId)
                    .orElse(null);

            Integer priorityScore = priorityScoreRepository
                    .findLatestScoreByCustomerId(customerId)
                    .orElse(null);

            BigDecimal outstandingAmount = accountRepository
                    .totalOutstandingByCustomerId(customerId)
                    .orElse(BigDecimal.ZERO);

            // 🔴 Prototype SLA logic (stub – correct place)
            String slaStatus = "On Track";

            return CustomerSearchMapper.toResponse(
                    customer,
                    priorityScore,
                    outstandingAmount,
                    slaStatus,
                    city
            );
        });
    }
}
