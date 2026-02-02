package com.example.backend.service.dashboard;

import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.entity.dca.DcaCase;
import com.example.backend.domain.enums.customer.AgeingBucket;
import com.example.backend.domain.enums.dca.CaseStatus;
import com.example.backend.domain.enums.payment.PaymentSource;
import com.example.backend.dto.response.dashboard.DashboardResponse;
import com.example.backend.dto.response.dashboard.DashboardStatsDTO;
import com.example.backend.dto.response.dashboard.AgingBucketDTO;
import com.example.backend.dto.response.dashboard.RecoveryByDcaDTO;
import com.example.backend.dto.response.dashboard.RecoveryTrendDTO;
import com.example.backend.repository.commission.DcaCommissionRepository;
import com.example.backend.repository.customer.CustomerAccountRepository;
import com.example.backend.repository.dca.DcaActionLogRepository;
import com.example.backend.repository.dca.DcaCaseRepository;
import com.example.backend.repository.dca.DcaRepository;
import com.example.backend.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DcaCaseRepository dcaCaseRepository;
    private final DcaActionLogRepository dcaActionLogRepository;
    private final CustomerAccountRepository customerAccountRepository;
    private final DcaCommissionRepository dcaCommissionRepository;
    private final PaymentRepository paymentRepository;
    private final DcaRepository dcaRepository;

    private static final DateTimeFormatter MONTH_FMT =
            DateTimeFormatter.ofPattern("MMM"); // Jan, Feb, Mar

    public DashboardResponse buildDashboard() {

        // =========================
        // 1️⃣ DASHBOARD STATS
        // =========================
        List<DcaCase> allCases = dcaCaseRepository.findAll();

        long openCases =
                allCases.stream()
                        .filter(c -> c.getCaseStatus() == CaseStatus.Open)
                        .count();

        long completedCases =
                allCases.stream()
                        .filter(c -> c.getCaseStatus() == CaseStatus.Closed)
                        .count();

        long pendingCases =
                allCases.stream()
                        .filter(c ->
                                c.getCaseStatus() == CaseStatus.Open &&
                                c.getCaseSlaDueDate() != null &&
                                !c.getCaseSlaDueDate().isBefore(LocalDate.now())
                        )
                        .count();

        long slaBreaches =
                dcaActionLogRepository.findAll().stream()
                        .filter(l -> Boolean.TRUE.equals(l.getSlaBreachFlag()))
                        .count();

        DashboardStatsDTO stats =
                new DashboardStatsDTO(
                        openCases,
                        completedCases,
                        pendingCases,
                        slaBreaches
                );

        // =========================
        // 2️⃣ AGING BUCKETS
        // Only OPEN cases + unpaid accounts
        // =========================
        Set<Long> openAccountIds =
                allCases.stream()
                        .filter(c -> c.getCaseStatus() == CaseStatus.Open)
                        .map(DcaCase::getAccountId)
                        .collect(Collectors.toSet());

        Map<String, Long> ageingMap =
                customerAccountRepository.findAll().stream()
                        .filter(acc ->
                                openAccountIds.contains(acc.getAccountId()) &&
                                acc.getOutstandingAmount() != null &&
                                acc.getOutstandingAmount().compareTo(BigDecimal.ZERO) > 0
                        )
                        .collect(
                                Collectors.groupingBy(
                                        acc -> acc.getAgeingBucket().getDbValue(),
                                        Collectors.counting()
                                )
                        );

        List<AgingBucketDTO> agingBuckets =
                Arrays.stream(AgeingBucket.values())
                        .map(b ->
                                new AgingBucketDTO(
                                        b.getDbValue(),
                                        ageingMap.getOrDefault(b.getDbValue(), 0L)
                                )
                        )
                        .toList();

        // =========================
        // 3️⃣ RECOVERY BY DCA
        // =========================
        Map<Long, BigDecimal> recoveredByDca =
                dcaCommissionRepository.findAll().stream()
                        .filter(c -> c.getRecoveredAmount() != null)
                        .collect(
                                Collectors.groupingBy(
                                        c -> c.getDcaId(),
                                        Collectors.reducing(
                                                BigDecimal.ZERO,
                                                c -> c.getRecoveredAmount(),
                                                BigDecimal::add
                                        )
                                )
                        );

        List<RecoveryByDcaDTO> recoveryByDca =
                dcaRepository.findAll().stream()
                        .map(dca ->
                                new RecoveryByDcaDTO(
                                        dca.getDcaName(),
                                        recoveredByDca.getOrDefault(
                                                dca.getDcaId(),
                                                BigDecimal.ZERO
                                        )
                                )
                        )
                        .toList();

        // =========================
        // 4️⃣ RECOVERY TREND (MONTHLY)
        // =========================
        Map<YearMonth, BigDecimal> recoveryTrendMap =
                paymentRepository.findAll().stream()
                        .filter(p ->
                                p.getPaymentSource() == PaymentSource.DCA &&
                                p.getPaymentAmount() != null &&
                                p.getPaymentDate() != null
                        )
                        .collect(
                                Collectors.groupingBy(
                                        p -> YearMonth.from(p.getPaymentDate()),
                                        Collectors.reducing(
                                                BigDecimal.ZERO,
                                                p -> p.getPaymentAmount(),
                                                BigDecimal::add
                                        )
                                )
                        );

        List<RecoveryTrendDTO> recoveryTrend =
                recoveryTrendMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .map(e ->
                                new RecoveryTrendDTO(
                                        e.getKey().format(MONTH_FMT),
                                        e.getValue()
                                )
                        )
                        .toList();

        // =========================
        // FINAL RESPONSE
        // =========================
        return new DashboardResponse(
                stats,
                agingBuckets,
                recoveryByDca,
                recoveryTrend
        );
    }
}
