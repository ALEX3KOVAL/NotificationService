package ru.alex3koval.notificationService.server.api.otp.dto.request.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import jakarta.validation.constraints.NotBlank;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
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
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);

        SendingRecipient phone = ctxt.readValue(
            node.get("phone").traverse(oc),
            SendingRecipient.class
        );

        @NotBlank
        String text = node.get("text").asText();

        OtpReason reason = ctxt.readValue(
            node.get("reason").traverse(oc),
            OtpReason.class
        );


        return new SendOtpViaPhoneRequest((Phone) phone, reason, text);
    }
}
