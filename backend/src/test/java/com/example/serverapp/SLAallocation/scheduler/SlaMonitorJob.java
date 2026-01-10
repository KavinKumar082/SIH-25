package com.example.serverapp.allocation.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.serverapp.allocation.model.DcaCase;
import com.example.serverapp.allocation.repository.DcaCaseRepository;

@Component
public class SlaMonitorJob {

    private final DcaCaseRepository repo;

    public SlaMonitorJob(DcaCaseRepository repo) {
        this.repo = repo;
    }

    @Scheduled(cron = "0 0 * * * *") // hourly
    public void monitor() {

        List<DcaCase> breached =
                repo.findByCaseStatusNotAndSlaDueDateBefore(
                        DcaCase.CaseStatus.CLOSED,
                        LocalDate.now());

        breached.forEach(c ->
                System.out.println("SLA BREACHED → CASE ID: " + c.getCaseId())
        );
    }
}
