package ru.alex3koval.notificationService.storage.converter.mailFormat;

import lombok.NonNull;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import ru.alex3koval.notificationService.domain.vo.MailFormat;

@ReadingConverter
public class ReadingMailFormatConverter implements Converter<Short, MailFormat> {
    @Override
    public MailFormat convert(@NonNull Short source) {
        return MailFormat
            .of(source)
            .orElseThrow(() ->
                new ConversionFailedException(
                    TypeDescriptor.valueOf(Short.class),
                    TypeDescriptor.valueOf(MailFormat.class),
                    source,
                    new IllegalArgumentException(
                        String.format(
                            "Не удалось провести конвертацию Short в %s",
                            MailFormat.class.getCanonicalName()
                        )
                    )
                )
            );
    }
}
