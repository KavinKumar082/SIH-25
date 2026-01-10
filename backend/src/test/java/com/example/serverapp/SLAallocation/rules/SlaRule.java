package com.example.serverapp.allocation.rules;

import java.time.LocalDate;

public class SlaRule {

    public static LocalDate calculate(double priorityScore) {

        if (priorityScore >= 80) return LocalDate.now().plusDays(1);
        if (priorityScore >= 60) return LocalDate.now().plusDays(3);
        return LocalDate.now().plusDays(7);
    }
}
