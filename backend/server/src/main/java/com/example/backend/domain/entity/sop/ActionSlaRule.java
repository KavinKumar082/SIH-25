package com.example.backend.domain.entity.sop;

import com.example.backend.converter.sop.ActionTypeConverter;
import com.example.backend.domain.enums.sop.ActionType;
import jakarta.persistence.*;

@Entity
@Table(name = "Action_SLA_Rule")
public class ActionSlaRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_sla_id", nullable = false, updatable = false)
    private Long actionSlaId;

    @Convert(converter = ActionTypeConverter.class)
    @Column(name = "action_type",
    columnDefinition = "ENUM('Call','Visit','Email')"
    )
    private ActionType actionType;


    @Column(name = "max_days_allowed")
    private Integer maxDaysAllowed;

    @Column(name = "mandatory_flag")
    private Boolean mandatoryFlag;

    // Getters & Setters
    public Long getActionSlaId() { return actionSlaId; }

    public ActionType getActionType() { return actionType; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }

    public Integer getMaxDaysAllowed() { return maxDaysAllowed; }
    public void setMaxDaysAllowed(Integer maxDaysAllowed) { this.maxDaysAllowed = maxDaysAllowed; }

    public Boolean getMandatoryFlag() { return mandatoryFlag; }
    public void setMandatoryFlag(Boolean mandatoryFlag) { this.mandatoryFlag = mandatoryFlag; }
}
