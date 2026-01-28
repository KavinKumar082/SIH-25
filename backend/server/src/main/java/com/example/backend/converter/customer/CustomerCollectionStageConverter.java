package com.example.backend.converter.customer;

import com.example.backend.domain.enums.customer.CollectionStage;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CustomerCollectionStageConverter
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
