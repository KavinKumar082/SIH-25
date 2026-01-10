package com.example.serverapp.allocation.rules;

import com.example.serverapp.allocation.model.CustomerAccount;

public class PriorityRule {

    public static double calculate(CustomerAccount acc) {

        if (acc.getAgeingBucket() == CustomerAccount.AgeingBucket.NINETY_PLUS
                || acc.getDelinquencyScore() >= 5) {
            return 90;
        }

        if (acc.getAgeingBucket() == CustomerAccount.AgeingBucket.SIXTY_ONE_TO_90) {
            return 70;
        }

        return 40;
    }
}
