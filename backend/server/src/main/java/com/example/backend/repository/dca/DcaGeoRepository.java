package com.example.backend.repository.dca;

import com.example.backend.domain.entity.dca.DcaGeo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DcaGeoRepository
        extends JpaRepository<DcaGeo, Long> {

    Optional<DcaGeo> findFirstByDcaDcaId(Long dcaId);
}
