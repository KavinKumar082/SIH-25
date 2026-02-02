package com.example.backend.repository.dca;

import com.example.backend.domain.entity.dca.DcaCase;
import com.example.backend.domain.entity.dca.DcaGeo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

import java.util.List;

public interface DcaCaseRepository extends JpaRepository<DcaCase, Long> {

    @Query("""
        SELECT dc
        FROM DcaCase dc
        WHERE dc.caseStatus = com.example.backend.domain.enums.dca.CaseStatus.Open
    """)
    List<DcaCase> findOpenCases();

    boolean existsByAccountId(Long accountId);

    List<DcaCase> findByDca_DcaId(Long dcaId);


    Optional<DcaGeo> findFirstByDcaDcaId(Long dcaId);
}
