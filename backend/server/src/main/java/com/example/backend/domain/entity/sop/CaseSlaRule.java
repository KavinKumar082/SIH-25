package com.example.backend.domain.entity.sop;

import com.example.backend.converter.sop.SeverityConverter;
import com.example.backend.converter.sop.SopCollectionStageConverter;
import com.example.backend.domain.enums.sop.CollectionStage;
import com.example.backend.domain.enums.sop.Severity;
import jakarta.persistence.*;

@Entity
@Table(name = "Case_SLA_Rule")
public class CaseSlaRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_sla_id", nullable = false, updatable = false)
    private Long caseSlaId;

    
    @Column(name = "max_resolution_days")
    private Integer maxResolutionDays;

    @Convert(converter = SopCollectionStageConverter.class)
    @Column(
        name = "collection_stage",
        columnDefinition = "ENUM('Pre_DCA','Active','Legal')"
    )
    private CollectionStage collectionStage;

    @Convert(converter = SeverityConverter.class)
    @Column(
        name = "severity",
        columnDefinition = "ENUM('Low','Medium','High')"
    )
    private Severity severity;


    // Getters & Setters
    public Long getCaseSlaId() { return caseSlaId; }

    public CollectionStage getCollectionStage() { return collectionStage; }
    public void setCollectionStage(CollectionStage collectionStage) { this.collectionStage = collectionStage; }

    public Integer getMaxResolutionDays() { return maxResolutionDays; }
    public void setMaxResolutionDays(Integer maxResolutionDays) { this.maxResolutionDays = maxResolutionDays; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
}
