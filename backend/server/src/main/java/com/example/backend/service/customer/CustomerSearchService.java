package com.example.backend.service.customer;

import com.example.backend.domain.entity.customer.Customer;
import com.example.backend.dto.request.customer.SearchFilterRequest;
import com.example.backend.dto.response.customer.CustomerSearchResponse;
import com.example.backend.mapper.customer.CustomerSearchMapper;
import com.example.backend.repository.customer.CustomerRepository;
import com.example.backend.repository.customer.CustomerSearchSpecification;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class CustomerSearchService {

    private final CustomerRepository customerRepository;

    public CustomerSearchService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public Page<CustomerSearchResponse> search(SearchFilterRequest request) {

        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getPageSize(),
                Sort.by(
                        Sort.Direction.fromString(request.getSortOrder()),
                        request.getSortField()
                )
        );

        Page<Customer> page = customerRepository.findAll(
                CustomerSearchSpecification.search(
                        request.getSearchTerm(),
                        request.getCity()
                ),
                pageable
        );

        return new PageImpl<>(
                page.getContent()
                        .stream()
                        .map(c ->
                                CustomerSearchMapper.toResponse(
                                        c,
                                        null // city derived later if needed
                                )
                        )
                        .collect(Collectors.toList()),
                pageable,
                page.getTotalElements()
        );
    }
}
