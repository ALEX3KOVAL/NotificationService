package ru.alex3koval.notificationService.appImpl.core.serialization.vo.deser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.alex3koval.notificationService.domain.vo.MailFormat;

import java.io.IOException;

public class MailFormatDeserializer extends JsonDeserializer<MailFormat> {
    @Override
    public MailFormat deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        short rawMailFormat = p.getShortValue();

        return MailFormat
            .of(rawMailFormat)
            .orElseThrow(() -> new RuntimeException(String.format("Не удалось десериализовать из: %s", rawMailFormat)));
    }
}
