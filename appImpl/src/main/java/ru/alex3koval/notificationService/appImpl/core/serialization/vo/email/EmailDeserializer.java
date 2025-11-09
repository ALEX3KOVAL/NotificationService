package ru.alex3koval.notificationService.appImpl.core.serialization.vo.email;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.alex3koval.notificationService.domain.vo.Email;

import java.io.IOException;

public class EmailDeserializer extends JsonDeserializer<Email> {
    @Override
    public Email deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken() != JsonToken.VALUE_STRING) {
            p.nextToken();
        }
        String rawEmail = p.getText();

        return (Email) Email
            .from(rawEmail)
            .orElseThrow(
                () -> new RuntimeException(String.format("Не удалось десериализовать из: %s", rawEmail))
            );
    }
}
