package com.example.serverapp.allocation.rules;

import java.util.List;

import com.example.serverapp.allocation.model.Dca;
import com.example.serverapp.allocation.model.DcaCase;
import com.example.serverapp.allocation.repository.DcaCaseRepository;

public class AllocationRule {

    public static Dca pickLeastLoaded(
            List<Dca> dcas,
            DcaCaseRepository repo) {

        return dcas.stream()
                .min((a, b) -> Long.compare(
                        repo.countByDcaIdAndCaseStatusNot(
                                a.getDcaId(), DcaCase.CaseStatus.CLOSED),
                        repo.countByDcaIdAndCaseStatusNot(
                                b.getDcaId(), DcaCase.CaseStatus.CLOSED)
                ))
                .orElseThrow();
    }
}
