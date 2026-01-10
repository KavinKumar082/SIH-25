package com.example.serverapp.allocation.dto;

public class AllocationRequestDto {

    // optional for future extension
    // currently allocation runs in batch

    private String triggeredBy;

    public String getTriggeredBy() {
        return triggeredBy;
    }

    public void setTriggeredBy(String triggeredBy) {
        this.triggeredBy = triggeredBy;
    }
}
