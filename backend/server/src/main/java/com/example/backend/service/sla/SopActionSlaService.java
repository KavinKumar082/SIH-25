package com.example.backend.service.sla;

import com.example.backend.domain.entity.customer.CustomerAccount;
import com.example.backend.domain.entity.dca.DcaActionLog;
import com.example.backend.domain.entity.dca.DcaCase;
import com.example.backend.domain.entity.sla.ActionSlaRule;
import com.example.backend.domain.entity.sla.SopActionSlaMap;
import com.example.backend.domain.enums.sla.CollectionStage;
import com.example.backend.repository.dca.DcaActionLogRepository;
import com.example.backend.repository.sla.SopActionSlaMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SopActionSlaService {

    private final SopActionSlaMapRepository sopActionSlaMapRepository;
    private final DcaActionLogRepository dcaActionLogRepository;

    public void generateActionSlas(DcaCase dcaCase, CustomerAccount account) {

        // enum conversion (customer → sla)
        CollectionStage stage =
                CollectionStage.valueOf(account.getCollectionStage().name());

        List<SopActionSlaMap> mappings =
                sopActionSlaMapRepository.findByCollectionStage(stage);

        for (SopActionSlaMap map : mappings) {

            ActionSlaRule rule = map.getActionSlaRule();

            DcaActionLog log = new DcaActionLog();
            log.setDcaCase(dcaCase);
            log.setSopId(map.getSopRule().getSopId());
            log.setActionSlaDueDate(
                    LocalDate.now().plusDays(rule.getMaxDaysAllowed())
            );
            log.setSlaBreachFlag(false);

            dcaActionLogRepository.save(log);
        }
    }
}
