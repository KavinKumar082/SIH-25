package com.example.backend.service.test;

import com.example.backend.domain.entity.commission.DcaCommission;
import com.example.backend.dto.response.test.CommissionUpdateResponse;
import com.example.backend.repository.commission.DcaCommissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Profile("test-write")
public class CommissionTestService {

    private final DcaCommissionRepository repository;

    public CommissionTestService(DcaCommissionRepository repository) {
        this.repository = repository;
    }

    @Transactional
public CommissionUpdateResponse updateCommissionAmount(
        Long commissionId,
        BigDecimal newAmount) {

    DcaCommission commission = repository.findById(commissionId)
            .orElseThrow(() -> new RuntimeException("Commission not found"));

    BigDecimal oldAmount = commission.getCommissionAmount();

    commission.setCommissionAmount(newAmount);
    repository.save(commission);

    return new CommissionUpdateResponse(
            commissionId,
            new CommissionUpdateResponse.Before(oldAmount),
            new CommissionUpdateResponse.After(newAmount)
    );
}


}