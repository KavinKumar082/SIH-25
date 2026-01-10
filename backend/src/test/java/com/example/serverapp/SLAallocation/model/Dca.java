package com.example.serverapp.allocation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dcas")
public class Dca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dca_id")
    private Long dcaId;

    @Column(name = "dca_name")
    private String dcaName;

    private String region;

    @Column(name = "commission_rate")
    private Double commissionRate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ACTIVE, SUSPENDED
    }

    // ✅ GETTERS (REQUIRED)
    public Long getDcaId() {
        return dcaId;
    }

    public String getDcaName() {
        return dcaName;
    }

    public String getRegion() {
        return region;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public Status getStatus() {
        return status;
    }

    // ✅ SETTERS (SAFE TO HAVE)
    public void setStatus(Status status) {
        this.status = status;
    }
}
