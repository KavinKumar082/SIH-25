package com.example.serverapp.allocation.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class SlaService {

    public LocalDate calculateSlaDueDate(double priorityScore, LocalDate allocatedDate) {

        int slaDays;

        if (priorityScore >= 9.0) {
            slaDays = 1;
        } else if (priorityScore >= 7.0) {
            slaDays = 3;
        } else if (priorityScore >= 4.0) {
            slaDays = 5;
        } else {
            slaDays = 7;
        }

        // ✅ REQUIRED RETURN
        return allocatedDate.plusDays(slaDays);
    }
}
