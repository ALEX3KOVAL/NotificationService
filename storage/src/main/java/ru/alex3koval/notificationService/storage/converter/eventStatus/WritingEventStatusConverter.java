package ru.alex3koval.notificationService.storage.converter.eventStatus;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import ru.alex3koval.eventingContract.vo.EventStatus;

@WritingConverter
public class WritingEventStatusConverter implements Converter<EventStatus, Short> {
    @Override
    public Short convert(EventStatus source) {
        return source.getValue();
    }
}
