package com.example.backend.repository.dca;

import com.example.backend.domain.entity.dca.Dca;
import com.example.backend.domain.enums.dca.DcaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DcaRepository extends JpaRepository<Dca, Long> {

    List<Dca> findByStatus(DcaStatus status);
}
