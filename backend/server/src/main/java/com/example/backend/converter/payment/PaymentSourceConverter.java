package com.example.backend.converter.payment;

import com.example.backend.domain.enums.payment.PaymentSource;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PaymentSourceConverter
        implements AttributeConverter<PaymentSource, String> {

    @Override
    public String convertToDatabaseColumn(PaymentSource attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public PaymentSource convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PaymentSource.valueOf(dbData);
    }
}
