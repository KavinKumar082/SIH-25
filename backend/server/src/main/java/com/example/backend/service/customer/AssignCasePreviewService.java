package com.example.backend.service.customer;

import com.example.backend.domain.entity.customer.Customer;
import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.enums.customer.CollectionStage;
import com.example.backend.domain.enums.customer.CustomerStatus;
import com.example.backend.dto.response.customer.AssignCasePreviewResponse;
import com.example.backend.repository.customer.AccountAddressRepository;
import com.example.backend.repository.customer.CustomerAccountRepository;
import com.example.backend.repository.customer.CustomerPriorityScoreRepository;
import com.example.backend.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class AssignCasePreviewService {

    private final CustomerRepository customerRepository;
    private final CustomerAccountRepository accountRepository;
    private final CustomerPriorityScoreRepository priorityScoreRepository;
    private final AccountAddressRepository addressRepository;

    public AssignCasePreviewService(
            CustomerRepository customerRepository,
            CustomerAccountRepository accountRepository,
            CustomerPriorityScoreRepository priorityScoreRepository,
            AccountAddressRepository addressRepository
    ) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.priorityScoreRepository = priorityScoreRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * STEP-5 (PREVIEW)
     * - In_Collections customers
     * - Pre_DCA / Active accounts only
     * - Latest STATIC priority (read-only)
     * - NOT assigned yet
     */
    public List<AssignCasePreviewResponse> fetchPreview() {

        return customerRepository
                .findByStatus(CustomerStatus.Active)
                .stream()
                .map(this::buildPreview)
                .filter(r -> r != null)
                .sorted(
                        Comparator.comparingInt(
                                AssignCasePreviewResponse::staticPriorityScore
                        ).reversed()
                )
                .toList();
    }

    // ------------------ INTERNAL ------------------

    private AssignCasePreviewResponse buildPreview(Customer customer) {

        // 1️⃣ Eligible accounts (Pre_DCA / Active)
        List<CustomerAccount> eligibleAccounts =
                accountRepository
                        .findByCustomerCustomerId(customer.getCustomerId())
                        .stream()
                        .filter(acc ->
                                acc.getCollectionStage() == CollectionStage.Pre_DCA
                                        || acc.getCollectionStage() == CollectionStage.Active
                        )
                        .toList();

        if (eligibleAccounts.isEmpty()) return null;

        // 2️⃣ Latest STATIC priority (already computed earlier)
        Integer priorityScore =
                priorityScoreRepository
                        .findLatestScoreByCustomerId(customer.getCustomerId())
                        .orElse(null);

        if (priorityScore == null) return null;

        // 3️⃣ Total outstanding (read-only)
        BigDecimal totalOutstanding =
                accountRepository
                        .totalOutstandingByCustomerId(customer.getCustomerId())
                        .orElse(BigDecimal.ZERO);

        // 4️⃣ City (first address only – deterministic)
        String city =
                addressRepository
                        .findCityByCustomerId(customer.getCustomerId())
                        .orElse("UNKNOWN");

        // 5️⃣ Ageing bucket (take highest pressure account)
        String ageingBucket =
                eligibleAccounts.stream()
                        .map(acc -> acc.getAgeingBucket().name())
                        .findFirst()
                        .orElse("UNKNOWN");

        // 6️⃣ PREVIEW response (NOT ASSIGNED)
        return new AssignCasePreviewResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                city,
                "DEFAULT_REGION",
                totalOutstanding,
                ageingBucket,
                priorityScore,
                false
        );
    }
}
