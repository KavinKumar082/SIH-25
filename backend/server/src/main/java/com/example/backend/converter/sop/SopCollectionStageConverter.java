package com.example.backend.converter.sop;

import com.example.backend.domain.enums.sop.CollectionStage;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SopCollectionStageConverter
        implements AttributeConverter<CollectionStage, String> {

    @Override
    public String convertToDatabaseColumn(CollectionStage attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public CollectionStage convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CollectionStage.valueOf(dbData);
    }
}
