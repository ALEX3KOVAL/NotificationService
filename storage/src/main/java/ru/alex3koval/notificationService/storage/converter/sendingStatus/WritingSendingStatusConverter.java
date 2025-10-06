package ru.alex3koval.notificationService.storage.converter.sendingStatus;


import org.springframework.data.convert.WritingConverter;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;
import org.springframework.core.convert.converter.Converter;

@WritingConverter
public class WritingSendingStatusConverter implements Converter<SendingStatus, String> {
    @Override
    public String convert(SendingStatus source) {
        return source.name().toLowerCase();
    }
}


