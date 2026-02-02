package com.example.backend.controller.dca;

import com.example.backend.dto.response.dca.DcaCaseDetailsResponse;
import com.example.backend.dto.response.dca.DcaMetricsResponse;
import com.example.backend.service.monitoring.DcaCaseQueryService;
import com.example.backend.service.monitoring.DcaMetricsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dcas")
@RequiredArgsConstructor
public class DcaMonitoringController {

    private final DcaMetricsQueryService metricsService;
    private final DcaCaseQueryService caseQueryService;

    @GetMapping("/metrics")
    public List<DcaMetricsResponse> getMetrics() {
        return metricsService.fetchMetrics();
    }

    @GetMapping("/{dcaId}/cases")
    public List<DcaCaseDetailsResponse> getCases(
            @PathVariable Long dcaId
    ) {
        return caseQueryService.fetchCases(dcaId);
    }
}
