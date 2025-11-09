package ru.alex3koval.notificationService.appImpl.core.serialization.vo.identifier;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.alex3koval.notificationService.domain.vo.Identifier;

import java.io.IOException;

public class IdentifierDeserializer extends JsonDeserializer<Identifier> {
    @Override
    public Identifier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken() != JsonToken.VALUE_STRING) {
            p.nextToken();
        }
        String rawRecipient = p.getText();

        return Identifier
            .from(rawRecipient)
            .orElseThrow(
                () -> new RuntimeException(String.format("Не удалось десериализовать из: %s", rawRecipient))
            );
    }
}
