package com.example.backend.converter.sop;

import com.example.backend.domain.enums.sop.ActionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ActionTypeConverter
        implements AttributeConverter<ActionType, String> {

    @Override
    public String convertToDatabaseColumn(ActionType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public ActionType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ActionType.valueOf(dbData);
    }
}
