package com.example.backend.repository.dca;

import com.example.backend.domain.entity.dca.DcaCapacity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DcaCapacityRepository
        extends JpaRepository<DcaCapacity, Long> {

    Optional<DcaCapacity> findByDcaDcaIdAndMonth(Long dcaId, LocalDate month);
}
