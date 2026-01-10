package com.example.serverapp.allocation.dto;

import java.time.LocalDate;

public class UpdateSlaRequestDto {

    private LocalDate allocatedDate;
    private LocalDate slaDueDate;

    public LocalDate getAllocatedDate() {
        return allocatedDate;
    }

    public void setAllocatedDate(LocalDate allocatedDate) {
        this.allocatedDate = allocatedDate;
    }

    public LocalDate getSlaDueDate() {
        return slaDueDate;
    }

    public void setSlaDueDate(LocalDate slaDueDate) {
        this.slaDueDate = slaDueDate;
    }
}
