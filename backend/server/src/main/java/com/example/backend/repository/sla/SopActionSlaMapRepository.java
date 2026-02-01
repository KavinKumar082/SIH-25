package com.example.backend.repository.sla;

import com.example.backend.domain.entity.sla.SopActionSlaMap;
import com.example.backend.domain.enums.sla.CollectionStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SopActionSlaMapRepository
        extends JpaRepository<SopActionSlaMap, Long> {

    List<SopActionSlaMap> findByCollectionStage(CollectionStage collectionStage);
}
