package ru.alex3koval.notificationService.storage.converter.sendingRecipient;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

@WritingConverter
public class WritingSendingRecipientConverter implements Converter<SendingRecipient, String> {
    @Override
    public String convert(SendingRecipient source) {
        return source.getValue();
    }
}
