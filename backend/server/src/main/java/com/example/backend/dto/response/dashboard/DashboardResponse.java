package com.example.backend.dto.response.dashboard;

import java.util.List;

public class DashboardResponse {

    private DashboardStatsDTO stats;
    private List<AgingBucketDTO> agingBuckets;
    private List<RecoveryByDcaDTO> recoveryByDca;
    private List<RecoveryTrendDTO> recoveryTrend;

    public DashboardResponse(
            DashboardStatsDTO stats,
            List<AgingBucketDTO> agingBuckets,
            List<RecoveryByDcaDTO> recoveryByDca,
            List<RecoveryTrendDTO> recoveryTrend
    ) {
        this.stats = stats;
        this.agingBuckets = agingBuckets;
        this.recoveryByDca = recoveryByDca;
        this.recoveryTrend = recoveryTrend;
    }

    public DashboardStatsDTO getStats() {
        return stats;
    }

    public List<AgingBucketDTO> getAgingBuckets() {
        return agingBuckets;
    }

    public List<RecoveryByDcaDTO> getRecoveryByDca() {
        return recoveryByDca;
    }

    public List<RecoveryTrendDTO> getRecoveryTrend() {
        return recoveryTrend;
    }
}
