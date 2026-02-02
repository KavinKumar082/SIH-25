package com.example.backend.dto.response.dashboard;

import java.math.BigDecimal;

public class RecoveryByDcaDTO {

    private String dcaName;
    private BigDecimal recoveredAmount;

    public RecoveryByDcaDTO(String dcaName, BigDecimal recoveredAmount) {
        this.dcaName = dcaName;
        this.recoveredAmount = recoveredAmount;
    }

    public String getDcaName() {
        return dcaName;
    }

    public BigDecimal getRecoveredAmount() {
        return recoveredAmount;
    }
}
