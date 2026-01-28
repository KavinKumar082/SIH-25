package com.example.backend.domain.entity.sop;

import com.example.backend.converter.sop.ActionTypeConverter;
import com.example.backend.domain.enums.sop.ActionType;
import jakarta.persistence.*;

@Entity
@Table(name = "SOP_Rule")
public class SopRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sop_id", nullable = false, updatable = false)
    private Long sopId;

    @Convert(converter = ActionTypeConverter.class)
    @Column(name = "action_type",
columnDefinition = "ENUM('Call','Visit','Email')"
)
private ActionType actionType;


    @Column(name = "description", length = 255)
    private String description;

    // Getters & Setters
    public Long getSopId() { return sopId; }

    public ActionType getActionType() { return actionType; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
