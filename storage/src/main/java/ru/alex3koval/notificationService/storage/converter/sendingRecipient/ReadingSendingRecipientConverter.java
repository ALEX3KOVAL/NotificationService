package ru.alex3koval.notificationService.storage.converter.sendingRecipient;

import lombok.NonNull;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

@ReadingConverter
public class ReadingSendingRecipientConverter implements Converter<String, SendingRecipient> {
    @Override
    public SendingRecipient convert(@NonNull String source) {
        return SendingRecipient.from(source).orElseThrow(() ->
            new ConversionFailedException(
                TypeDescriptor.valueOf(String.class),
                TypeDescriptor.valueOf(SendingRecipient.class),
                source,
                new IllegalArgumentException(
                    String.format(
                        "Не удалось провести конвертацию String в %s",
                        SendingRecipient.class.getCanonicalName()
                    )
                )
            )
        );
    }
}
