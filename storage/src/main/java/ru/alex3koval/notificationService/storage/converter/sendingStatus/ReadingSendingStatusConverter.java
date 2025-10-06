package ru.alex3koval.notificationService.storage.converter.sendingStatus;

import org.springframework.data.convert.ReadingConverter;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;
import org.springframework.core.convert.converter.Converter;

@ReadingConverter
public class ReadingSendingStatusConverter implements Converter<String, SendingStatus> {
    @Override
    public SendingStatus convert(String source) {
        return SendingStatus.valueOf(source.toUpperCase());
    }
}
