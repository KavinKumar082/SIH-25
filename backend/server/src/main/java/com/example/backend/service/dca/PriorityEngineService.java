package com.example.backend.service.dca;

/**
 * Dynamic Priority Engine.
 *
 * Responsible for recomputing case-level dynamic priority
 * based on static customer priority, SLA pressure,
 * and operational signals.
 *
 * IMPORTANT:
 * - This service MUST recompute fresh every run
 * - It MUST NOT use previous dynamic priority values
 * - It MUST NOT assign cases or touch DCA capacity
 */
public interface PriorityEngineService {

    /**
     * Recomputes dynamic priority for all eligible cases.
     *
     * This method is intended to be triggered by a scheduler.
     * It performs a full recalculation and persists results
     * to case_priority_score.
     */
    void recomputeDynamicPriorities();
}
