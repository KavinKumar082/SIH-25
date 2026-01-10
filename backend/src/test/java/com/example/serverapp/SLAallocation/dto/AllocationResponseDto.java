package com.example.serverapp.allocation.dto;

public class AllocationResponseDto {

    private String status;
    private String message;

    // ✅ REQUIRED: no-arg constructor
    public AllocationResponseDto() {
    }

    // ✅ convenience constructor
    public AllocationResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // ✅ getters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // ✅ setters (important for Jackson)
    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
