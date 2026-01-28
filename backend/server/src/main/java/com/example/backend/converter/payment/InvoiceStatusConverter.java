package com.example.backend.converter.payment;

import com.example.backend.domain.enums.payment.InvoiceStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class InvoiceStatusConverter
        implements AttributeConverter<InvoiceStatus, String> {

    @Override
    public String convertToDatabaseColumn(InvoiceStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public InvoiceStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : InvoiceStatus.valueOf(dbData);
    }
}
