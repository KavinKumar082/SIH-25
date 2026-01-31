package com.example.backend.scheduler.priority;

import com.example.backend.service.dca.PriorityEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CasePriorityScheduler {

    private final PriorityEngineService priorityEngineService;

    @Scheduled(cron = "0 0 */6 * * *")
    public void runPriorityEngine() {
        priorityEngineService.recomputeDynamicPriorities();
    }
}
