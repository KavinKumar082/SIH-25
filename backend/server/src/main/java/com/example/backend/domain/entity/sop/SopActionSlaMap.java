    package com.example.backend.domain.entity.sop;

import com.example.backend.converter.sop.SopCollectionStageConverter;
import com.example.backend.domain.enums.sop.CollectionStage;
import jakarta.persistence.*;

@Entity
@Table(name = "SOP_Action_SLA_Map")
public class SopActionSlaMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id", nullable = false, updatable = false)
    private Long mapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sop_id")
    private SopRule sopRule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_sla_id")
    private ActionSlaRule actionSlaRule;

    @Convert(converter = SopCollectionStageConverter.class)
    @Column(
        name = "collection_stage",
        columnDefinition = "ENUM('Pre_DCA','Active','Legal')"
    )
    private CollectionStage collectionStage;


    // Getters & Setters
    public Long getMapId() { return mapId; }

    public SopRule getSopRule() { return sopRule; }
    public void setSopRule(SopRule sopRule) { this.sopRule = sopRule; }

    public ActionSlaRule getActionSlaRule() { return actionSlaRule; }
    public void setActionSlaRule(ActionSlaRule actionSlaRule) { this.actionSlaRule = actionSlaRule; }

    public CollectionStage getCollectionStage() { return collectionStage; }
    public void setCollectionStage(CollectionStage collectionStage) { this.collectionStage = collectionStage; }
}
