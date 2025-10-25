package ru.alex3koval.notificationService.storage.converter.identifier;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import ru.alex3koval.notificationService.domain.vo.Identifier;

@WritingConverter
public class WritingIdentifierConverter implements Converter<Identifier, String> {
    @Override
    public String convert(Identifier source) {
        return source.getValue();
    }
}
