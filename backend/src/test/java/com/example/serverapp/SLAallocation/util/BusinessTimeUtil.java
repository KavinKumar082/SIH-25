package com.example.serverapp.allocation.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class BusinessTimeUtil {

    private BusinessTimeUtil() {
        // utility class
    }

    public static boolean isBusinessDay(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }
}
