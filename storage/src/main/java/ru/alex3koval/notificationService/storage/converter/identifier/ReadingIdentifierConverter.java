package ru.alex3koval.notificationService.storage.converter.identifier;

import lombok.NonNull;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import ru.alex3koval.notificationService.domain.vo.Identifier;

@ReadingConverter
public class ReadingIdentifierConverter implements Converter<String, Identifier> {
    @Override
    public Identifier convert(@NonNull String source) {
        return Identifier
            .from(source)
            .orElseThrow(() ->
                new ConversionFailedException(
                    TypeDescriptor.valueOf(String.class),
                    TypeDescriptor.valueOf(Identifier.class),
                    source,
                    new IllegalArgumentException(
                        String.format(
                            "Не удалось провести конвертацию String в %s",
                            Identifier.class.getCanonicalName()
                        )
                    )
                )
            );
    }
}
