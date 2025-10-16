package ru.alex3koval.notificationService.storage.converter.mailFormat;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import ru.alex3koval.notificationService.domain.vo.MailFormat;

@WritingConverter
public class WritingMailFormatConverter implements Converter<MailFormat, Short> {
    @Override
    public Short convert(@NonNull MailFormat source) {
        return source.getValue();
    }
}
