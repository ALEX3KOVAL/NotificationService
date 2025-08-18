package ru.alex3koval.notificationService.storage.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;

@Converter(autoApply = true)
public class SendingStatusConverter implements AttributeConverter<SendingStatus, Short> {

    @Override
    public Short convertToDatabaseColumn(SendingStatus attribute) {
        return attribute.getValue();
    }

    @Override
    public SendingStatus convertToEntityAttribute(Short dbData) {
        return SendingStatus
            .of(dbData)
            .orElseThrow(() -> new IllegalArgumentException("Не найдено значение статуса отправки: " + dbData));
    }
}
