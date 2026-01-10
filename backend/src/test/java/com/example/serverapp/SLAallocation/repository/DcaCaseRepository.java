package com.example.serverapp.allocation.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.serverapp.allocation.model.DcaCase;

public interface DcaCaseRepository extends JpaRepository<DcaCase, Long> {

    long countByDcaIdAndCaseStatusNot(
            Long dcaId,
            DcaCase.CaseStatus status
    );

    List<DcaCase> findByCaseStatusNotAndSlaDueDateBefore(
            DcaCase.CaseStatus status,
            LocalDate date
    );
}
