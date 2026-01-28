package com.example.backend.domain.entity.dca;

import com.example.backend.converter.dca.DcaStatusConverter;
import com.example.backend.domain.enums.dca.DcaStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "DCA")
public class Dca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dca_id", nullable = false, updatable = false)
    private Long dcaId;

    @Column(name = "dca_name", length = 150)
    private String dcaName;

    @Column(name = "commission_rate", precision = 5, scale = 2)
    private BigDecimal commissionRate;

    @Convert(converter = DcaStatusConverter.class)
    @Column(name = "status",columnDefinition = "ENUM('Active','Suspended')")
    private DcaStatus status;


    // Getters & Setters

    public Long getDcaId() {
        return dcaId;
    }

    public String getDcaName() {
        return dcaName;
    }

    public void setDcaName(String dcaName) {
        this.dcaName = dcaName;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public DcaStatus getStatus() {
        return status;
    }

    public void setStatus(DcaStatus status) {
        this.status = status;
    }
}
