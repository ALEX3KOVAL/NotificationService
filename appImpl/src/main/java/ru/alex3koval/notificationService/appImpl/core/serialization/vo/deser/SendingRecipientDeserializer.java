package ru.alex3koval.notificationService.appImpl.core.serialization.vo.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.io.IOException;

public class SendingRecipientDeserializer extends JsonDeserializer<SendingRecipient> {
    @Override
    public SendingRecipient deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String rawRecipient = p.getValueAsString();

        return SendingRecipient
            .from(rawRecipient)
            .orElseThrow(
                () -> new RuntimeException(String.format("Не удалось десериализовать из: %s", rawRecipient))
            );
    }
}
