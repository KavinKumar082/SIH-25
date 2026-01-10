package com.example.serverapp.allocation.model;

import java.time.LocalDate;

import jakarta.persistence.Column;   // ✅ REQUIRED IMPORT
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dca_cases")
public class DcaCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id")
    private Long caseId;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "dca_id")
    private Long dcaId;

    @Column(name = "allocated_date")
    private LocalDate allocatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "case_status")
    private CaseStatus caseStatus;

    @Column(name = "priority_score")
    private Double priorityScore;

    @Column(name = "sla_due_date")
    private LocalDate slaDueDate;

    @Column(name = "current_owner")
    private String currentOwner;

    public enum CaseStatus {
        OPEN, IN_PROGRESS, CLOSED
    }

    // ✅ GETTERS
    public Long getCaseId() {
        return caseId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getDcaId() {
        return dcaId;
    }

    public LocalDate getAllocatedDate() {
        return allocatedDate;
    }

    public LocalDate getSlaDueDate() {
        return slaDueDate;
    }

    public CaseStatus getCaseStatus() {
        return caseStatus;
    }

    // ✅ SETTERS (CRITICAL)
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setDcaId(Long dcaId) {
        this.dcaId = dcaId;
    }

    public void setAllocatedDate(LocalDate allocatedDate) {
        this.allocatedDate = allocatedDate;
    }

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
    }

    public void setPriorityScore(Double priorityScore) {
        this.priorityScore = priorityScore;
    }

    public void setSlaDueDate(LocalDate slaDueDate) {
        this.slaDueDate = slaDueDate;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }
    public Double getPriorityScore() {
    return priorityScore;
}

public String getCurrentOwner() {
    return currentOwner;
}

}
