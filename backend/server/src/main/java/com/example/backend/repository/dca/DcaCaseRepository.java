package com.example.backend.repository.dca;

import com.example.backend.domain.entity.dca.DcaCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DcaCaseRepository extends JpaRepository<DcaCase, Long> {

    @Query("""
        SELECT dc
        FROM DcaCase dc
        WHERE dc.caseStatus = com.example.backend.domain.enums.dca.CaseStatus.Open
    """)
    List<DcaCase> findOpenCases();
}
