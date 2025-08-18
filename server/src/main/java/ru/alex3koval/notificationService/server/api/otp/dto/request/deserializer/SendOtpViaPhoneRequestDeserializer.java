package ru.alex3koval.notificationService.server.api.otp.dto.request.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import jakarta.validation.constraints.NotBlank;
import ru.alex3koval.notificationService.domain.vo.Phone;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpViaPhoneRequest;

import java.io.IOException;

public class SendOtpViaPhoneRequestDeserializer extends StdDeserializer<SendOtpViaPhoneRequest> {
    public SendOtpViaPhoneRequestDeserializer() {
        super(SendOtpViaPhoneRequest.class);
    }

    @Override
    public SendOtpViaPhoneRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        String rawPhone = node.get("phone").asText();

        SendingRecipient phone = Phone
            .of(rawPhone)
            .orElseThrow(() -> new IllegalArgumentException("Клиент передал некорректный номер телефона: " + rawPhone));

        @NotBlank
        String text = node.get("text").asText();

        return new SendOtpViaPhoneRequest(phone, text);
    }
}
