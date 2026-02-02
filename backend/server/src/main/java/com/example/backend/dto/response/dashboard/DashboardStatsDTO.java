package com.example.backend.dto.response.dashboard;

public class DashboardStatsDTO {

    private long openCases;
    private long completedCases;
    private long pendingCases;
    private long slaBreaches;

    public DashboardStatsDTO(
            long openCases,
            long completedCases,
            long pendingCases,
            long slaBreaches
    ) {
        this.openCases = openCases;
        this.completedCases = completedCases;
        this.pendingCases = pendingCases;
        this.slaBreaches = slaBreaches;
    }

    public long getOpenCases() {
        return openCases;
    }

    public long getCompletedCases() {
        return completedCases;
    }

    public long getPendingCases() {
        return pendingCases;
    }

    public long getSlaBreaches() {
        return slaBreaches;
    }
}
