package ru.alex3koval.notificationService.appImpl.core.serialization.vo.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

import java.io.IOException;

public class SendingReasonDeserializer extends JsonDeserializer<SendingReason> {
    @Override
    public SendingReason deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken() != JsonToken.VALUE_NUMBER_INT) {
            p.nextToken();
        }
        short rawSendingReason = p.getShortValue();

        return SendingReason
            .of(rawSendingReason)
            .orElseThrow(() -> new RuntimeException(String.format("Не удалось десериализовать из: %s", rawSendingReason)));
    }
}
