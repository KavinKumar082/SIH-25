package com.example.backend.converter.dca;

import com.example.backend.domain.enums.dca.CaseStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CaseStatusConverter
        implements AttributeConverter<CaseStatus, String> {

    @Override
    public String convertToDatabaseColumn(CaseStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public CaseStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CaseStatus.valueOf(dbData);
    }
}
