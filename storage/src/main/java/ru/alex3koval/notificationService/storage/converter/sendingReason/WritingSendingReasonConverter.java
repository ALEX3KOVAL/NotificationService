package ru.alex3koval.notificationService.storage.converter.sendingReason;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

@WritingConverter
public class WritingSendingReasonConverter implements Converter<SendingReason, Short> {
    @Override
    public Short convert(SendingReason source) {
        return source.getValue();
    }
}
