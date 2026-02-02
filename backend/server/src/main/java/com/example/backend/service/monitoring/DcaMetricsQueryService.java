package com.example.backend.service.monitoring;

import com.example.backend.domain.entity.dca.Dca;
import com.example.backend.domain.entity.dca.DcaCase;
import com.example.backend.domain.enums.dca.CaseStatus;
import com.example.backend.domain.enums.dca.DcaStatus;
import com.example.backend.dto.response.dca.DcaMetricsResponse;
import com.example.backend.repository.dca.DcaCaseRepository;
import com.example.backend.repository.dca.DcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DcaMetricsQueryService {

    private final DcaRepository dcaRepository;
    private final DcaCaseRepository dcaCaseRepository;

    public List<DcaMetricsResponse> fetchMetrics() {

        return dcaRepository.findByStatus(DcaStatus.Active)
                .stream()
                .map(this::buildMetrics)
                .toList();
    }

    private DcaMetricsResponse buildMetrics(Dca dca) {

        List<DcaCase> cases =
                dcaCaseRepository.findByDca_DcaId(dca.getDcaId());

        long totalCases = cases.size();

        long openCases = cases.stream()
                .filter(c -> c.getCaseStatus() == CaseStatus.Open)
                .count();

        long closedCases = cases.stream()
                .filter(c -> c.getCaseStatus() == CaseStatus.Closed)
                .count();

        int recoveryPercentage =
                totalCases == 0
                        ? 0
                        : (int) Math.round((closedCases * 100.0) / totalCases);

        return new DcaMetricsResponse(
                dca.getDcaId(),
                dca.getDcaName(),
                totalCases,
                openCases,
                closedCases,
                recoveryPercentage
        );
    }
}
