package com.example.serverapp.allocation.service;

import org.springframework.stereotype.Service;

import com.example.serverapp.allocation.model.DcaCase;

@Service
public class EscalationService {

    public void escalate(DcaCase dcaCase) {
        // For now just log escalation
        // Later: notify supervisor / reassign / metrics update

        System.out.println(
            "Escalation triggered for Case ID: " + dcaCase.getCaseId()
        );
    }
}
