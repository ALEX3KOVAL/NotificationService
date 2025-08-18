package ru.alex3koval.notificationService.storage.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

@Converter(autoApply = true)
public class SendingRecipientConverter implements AttributeConverter<SendingRecipient, String> {

    @Override
    public String convertToDatabaseColumn(SendingRecipient attribute) {
        return attribute.getValue();
    }

    @Override
    public SendingRecipient convertToEntityAttribute(String dbData) {
        return SendingRecipient
            .from(dbData)
            .orElseThrow(() -> new IllegalArgumentException("Значение не является ни одним из подтипов получателя: " + dbData));
    }
}
