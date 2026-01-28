package com.example.backend.converter.sop;

import com.example.backend.domain.enums.sop.Severity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SeverityConverter
        implements AttributeConverter<Severity, String> {

    @Override
    public String convertToDatabaseColumn(Severity attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public Severity convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Severity.valueOf(dbData);
    }
}
