package ru.alex3koval.notificationService.appImpl.core.serialization.vo.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.alex3koval.notificationService.domain.vo.OtpReason;

import java.io.IOException;

public class OtpReasonDeserializer extends JsonDeserializer<OtpReason> {
    @Override
    public OtpReason deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String rawOtpReason = p.getValueAsString();

        return OtpReason
            .of(rawOtpReason)
            .orElseThrow(() -> new RuntimeException(String.format("Не удалось десериализовать из: %s", rawOtpReason)));
    }
}
