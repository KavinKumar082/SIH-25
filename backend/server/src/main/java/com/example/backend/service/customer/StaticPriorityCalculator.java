package com.example.backend.service.customer;

import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.enums.customer.AgeingBucket;
import com.example.backend.domain.enums.customer.RiskSegment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class StaticPriorityCalculator {

    private StaticPriorityCalculator() {}

    public record Result(int score, Map<String, Object> reason) {}

    public static Result calculate(CustomerAccount account) {

        int score = 0;
        Map<String, Object> reason = new HashMap<>();

        // 1️⃣ Outstanding amount
        BigDecimal outstanding = account.getOutstandingAmount();
        if (outstanding != null) {
            if (outstanding.compareTo(BigDecimal.valueOf(1_000_000)) >= 0) {
                score += 40;
                reason.put("outstanding", ">= 1Cr");
            } else if (outstanding.compareTo(BigDecimal.valueOf(500_000)) >= 0) {
                score += 30;
                reason.put("outstanding", ">= 5L");
            } else if (outstanding.compareTo(BigDecimal.valueOf(100_000)) >= 0) {
                score += 20;
                reason.put("outstanding", ">= 1L");
            } else {
                score += 10;
                reason.put("outstanding", "< 1L");
            }
        }

        // 2️⃣ Ageing bucket
        AgeingBucket ageing = account.getAgeingBucket();
        if (ageing != null) {
            switch (ageing) {
                case DAYS_0_30 -> { score += 5; reason.put("ageing", "0-30"); }
                case DAYS_31_60 -> { score += 15; reason.put("ageing", "31-60"); }
                case DAYS_61_90 -> { score += 25; reason.put("ageing", "61-90"); }
                case DAYS_90_PLUS -> { score += 35; reason.put("ageing", "90+"); }
            }
        }

        // 3️⃣ Risk segment
        RiskSegment risk = account.getRiskSegment();
        if (risk != null) {
            switch (risk) {
                case Low -> { score += 5; reason.put("risk", "LOW"); }
                case Medium -> { score += 15; reason.put("risk", "MEDIUM"); }
                case High -> { score += 30; reason.put("risk", "HIGH"); }
            }
        }

        // 4️⃣ Delinquency score
        Integer delinquency = account.getDelinquencyScore();
        if (delinquency != null) {
            int penalty = Math.min(delinquency / 10, 20);
            score += penalty;
            reason.put("delinquency", penalty);
        }

        return new Result(score, reason);
    }
}
