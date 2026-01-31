package com.example.backend.repository.dca;

import com.example.backend.domain.entity.dca.DcaActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DcaActionLogRepository
        extends JpaRepository<DcaActionLog, Long> {

    @Query("""
        SELECT dal
        FROM DcaActionLog dal
        WHERE dal.dcaCase.caseId = :caseId
          AND dal.slaBreachFlag = true
    """)
    List<DcaActionLog> findBreachedActionsByCaseId(
            @Param("caseId") Long caseId
    );
}
