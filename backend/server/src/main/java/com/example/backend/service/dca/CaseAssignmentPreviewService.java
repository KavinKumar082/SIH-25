package com.example.backend.service.dca;

import com.example.backend.domain.entity.customer.AccountAddress;
import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.entity.customer.CustomerPriorityScore;
import com.example.backend.domain.entity.dca.Dca;
import com.example.backend.domain.entity.dca.DcaGeo;
import com.example.backend.domain.enums.customer.CollectionStage;
import com.example.backend.domain.enums.dca.DcaStatus;
import com.example.backend.dto.response.dca.CaseAssignmentPreviewResponse;
import com.example.backend.repository.customer.AccountAddressRepository;
import com.example.backend.repository.customer.CustomerAccountRepository;
import com.example.backend.repository.customer.CustomerPriorityScoreRepository;
import com.example.backend.repository.dca.DcaCapacityRepository;
import com.example.backend.repository.dca.DcaCaseRepository;
import com.example.backend.repository.dca.DcaGeoRepository;
import com.example.backend.repository.dca.DcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseAssignmentPreviewService {

    private final CustomerAccountRepository customerAccountRepository;
    private final CustomerPriorityScoreRepository customerPriorityScoreRepository;
    private final AccountAddressRepository accountAddressRepository;
    private final DcaCaseRepository dcaCaseRepository;
    private final DcaRepository dcaRepository;
    private final DcaGeoRepository dcaGeoRepository;
    private final DcaCapacityRepository dcaCapacityRepository;

    /**
     * CORE PREVIEW LOGIC
     */
    public List<CaseAssignmentPreviewResponse> preview() {

        // 1️⃣ Already assigned accounts
        List<Long> assignedAccountIds =
                dcaCaseRepository.findAll()
                        .stream()
                        .map(dc -> dc.getAccountId())
                        .toList();

        // 2️⃣ Eligible accounts
        List<CustomerAccount> eligibleAccounts =
                customerAccountRepository.findAll()
                        .stream()
                        .filter(acc ->
                                (acc.getCollectionStage() == CollectionStage.Pre_DCA
                                 || acc.getCollectionStage() == CollectionStage.Active)
                                        && !assignedAccountIds.contains(acc.getAccountId())
                        )
                        .toList();

        // 3️⃣ Build preview sorted by static priority DESC
        return eligibleAccounts.stream()
                .map(this::buildPreview)
                .filter(p -> p != null)
                .sorted(
                        Comparator.comparingInt(
                                CaseAssignmentPreviewResponse::staticPriorityScore
                        ).reversed()
                )
                .toList();
    }

    // 🔁 Alias for controller clarity
    public List<CaseAssignmentPreviewResponse> fetchPreview() {
        return preview();
    }

    // 🔁 Alias for recommendation API
    public List<CaseAssignmentPreviewResponse> fetchRecommendations() {
        return preview();
    }

    // -------------------------------------------------

    private CaseAssignmentPreviewResponse buildPreview(CustomerAccount account) {

        CustomerPriorityScore staticScore =
                customerPriorityScoreRepository
                        .findTopByCustomerCustomerIdOrderByEffectiveDateDesc(
                                account.getCustomer().getCustomerId()
                        )
                        .orElse(null);

        if (staticScore == null) return null;

        AccountAddress addr =
                accountAddressRepository
                        .findFirstByAccountAccountId(account.getAccountId())
                        .orElse(null);

        if (addr == null
                || addr.getLatitude() == null
                || addr.getLongitude() == null) {
            return null;
        }

        Dca bestDca =
                dcaRepository.findByStatus(DcaStatus.Active)
                        .stream()
                        .filter(this::hasCapacity)
                        .max(Comparator.comparingInt(
                                dca -> suitabilityScore(dca, addr)
                        ))
                        .orElse(null);

        if (bestDca == null) return null;

        return new CaseAssignmentPreviewResponse(
                account.getAccountId(),
                staticScore.getScore(),
                bestDca.getDcaId(),
                bestDca.getDcaName()
        );
    }

    // -------------------------------------------------

    private boolean hasCapacity(Dca dca) {
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

        return dcaCapacityRepository
                .findByDcaDcaIdAndMonth(dca.getDcaId(), currentMonth)
                .map(cap -> cap.getAllocatedCases() < cap.getMaxCases())
                .orElse(false);
    }

    private int suitabilityScore(Dca dca, AccountAddress addr) {

        DcaGeo geo =
                dcaGeoRepository.findFirstByDcaDcaId(dca.getDcaId())
                        .orElse(null);

        if (geo == null
                || geo.getLatitude() == null
                || geo.getLongitude() == null) {
            return 0;
        }

        double distance =
                distance(
                        addr.getLatitude().doubleValue(),
                        addr.getLongitude().doubleValue(),
                        geo.getLatitude().doubleValue(),
                        geo.getLongitude().doubleValue()
                );

        int geoScore = distance < 50 ? 40 : distance < 150 ? 20 : 5;
        return geoScore + 30;
    }

    private double distance(
            double lat1, double lon1,
            double lat2, double lon2
    ) {
        double dx = lat1 - lat2;
        double dy = lon1 - lon2;
        return Math.sqrt(dx * dx + dy * dy) * 111;
    }
}
