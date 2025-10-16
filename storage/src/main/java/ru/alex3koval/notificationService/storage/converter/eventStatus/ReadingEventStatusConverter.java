package ru.alex3koval.notificationService.storage.converter.eventStatus;

import lombok.NonNull;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import ru.alex3koval.eventingContract.vo.EventStatus;

@ReadingConverter
public class ReadingEventStatusConverter implements Converter<Short, EventStatus> {
    @Override
    public EventStatus convert(@NonNull Short source) {
        return EventStatus
            .of(source)
            .orElseThrow(() ->
                new ConversionFailedException(
                    TypeDescriptor.valueOf(Short.class),
                    TypeDescriptor.valueOf(EventStatus.class),
                    source,
                    new IllegalArgumentException(
                        String.format(
                            "Не удалось провести конвертацию String в %s",
                            EventStatus.class.getCanonicalName()
                        )
                    )
                )
            );
    }
}
