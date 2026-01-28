package com.example.backend.converter.customer;

import com.example.backend.domain.enums.customer.RiskSegment;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RiskSegmentConverter
        implements AttributeConverter<RiskSegment, String> {

    @Override
    public String convertToDatabaseColumn(RiskSegment attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public RiskSegment convertToEntityAttribute(String dbData) {
        return dbData == null ? null : RiskSegment.valueOf(dbData);
    }
}
