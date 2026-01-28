package com.example.backend.converter.customer;

import com.example.backend.domain.enums.customer.CustomerStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CustomerStatusConverter
        implements AttributeConverter<CustomerStatus, String> {

    @Override
    public String convertToDatabaseColumn(CustomerStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public CustomerStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CustomerStatus.valueOf(dbData);
    }
}
