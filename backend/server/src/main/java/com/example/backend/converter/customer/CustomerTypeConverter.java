package com.example.backend.converter.customer;

import com.example.backend.domain.enums.customer.CustomerType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CustomerTypeConverter
        implements AttributeConverter<CustomerType, String> {

    @Override
    public String convertToDatabaseColumn(CustomerType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public CustomerType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CustomerType.valueOf(dbData);
    }
}
