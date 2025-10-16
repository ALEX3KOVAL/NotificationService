package ru.alex3koval.notificationService.storage.converter.sendingReason;

import lombok.NonNull;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

@ReadingConverter
public class ReadingSendingReasonConverter implements Converter<Short, SendingReason> {
    @Override
    public SendingReason convert(@NonNull Short source) {
        return SendingReason
            .of(source)
            .orElseThrow(() ->
                new ConversionFailedException(
                    TypeDescriptor.valueOf(Short.class),
                    TypeDescriptor.valueOf(SendingReason.class),
                    source,
                    new IllegalArgumentException(
                        String.format(
                            "Не удалось провести конвертацию Short в %s",
                            SendingReason.class.getCanonicalName()
                        )
                    )
                )
        );
    }
}
