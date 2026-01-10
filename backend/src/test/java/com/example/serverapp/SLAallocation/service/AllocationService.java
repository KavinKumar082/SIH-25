package com.example.serverapp.allocation.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.serverapp.allocation.model.DcaCase;
import com.example.serverapp.allocation.repository.DcaCaseRepository;

@Service
public class AllocationService {

    private final DcaCaseRepository caseRepo;
    private final SlaService slaService;

    public AllocationService(DcaCaseRepository caseRepo, SlaService slaService) {
        this.caseRepo = caseRepo;
        this.slaService = slaService;
    }

    @Transactional
    public void applyAllocationAndSla() {

        List<DcaCase> cases = caseRepo.findAll();

        for (DcaCase dc : cases) {

            // ✅ Skip already processed cases
            if (dc.getAllocatedDate() != null) {
                continue;
            }

            // ✅ Allocation timestamp
            LocalDate allocatedDate = LocalDate.now();

            // ✅ SLA derived from priority score
            LocalDate slaDueDate = slaService.calculateSlaDueDate(
                    dc.getPriorityScore(),
                    allocatedDate
            );

            // ✅ Persist to DB
            dc.setAllocatedDate(allocatedDate);
            dc.setSlaDueDate(slaDueDate);

            caseRepo.save(dc);
        }
    }
}
