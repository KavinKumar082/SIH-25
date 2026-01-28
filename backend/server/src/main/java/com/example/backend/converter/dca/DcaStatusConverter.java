package com.example.backend.converter.dca;

import com.example.backend.domain.enums.dca.DcaStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DcaStatusConverter
        implements AttributeConverter<DcaStatus, String> {

    @Override
    public String convertToDatabaseColumn(DcaStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public DcaStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : DcaStatus.valueOf(dbData);
    }
}
