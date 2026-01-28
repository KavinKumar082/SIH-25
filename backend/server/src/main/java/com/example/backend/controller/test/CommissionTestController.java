package com.example.backend.controller.test;

import com.example.backend.dto.response.test.CommissionUpdateResponse;
import com.example.backend.service.test.CommissionTestService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/test/commission")
@Profile("test-write")
public class CommissionTestController {

    private final CommissionTestService service;

    public CommissionTestController(CommissionTestService service) {
        this.service = service;
    }

    @PutMapping("/{commissionId}/amount")
public CommissionUpdateResponse updateCommissionAmount(
        @PathVariable Long commissionId,
        @RequestParam BigDecimal newAmount) {

    return service.updateCommissionAmount(commissionId, newAmount);
}

}
