package ru.alex3koval.notificationService.server.api.otp.dto.request.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import jakarta.validation.constraints.NotBlank;
import ru.alex3koval.notificationService.domain.vo.Email;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpMailRequest;
import ru.alex3koval.notificationService.server.exception.CustomDeserializationException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class SendMailRequestDeserializer extends StdDeserializer<SendOtpMailRequest> {
    public SendMailRequestDeserializer() {
        super(SendOtpMailRequest.class);
    }

    @Override
    public SendOtpMailRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        String rawRecipientEmail = node.get("recipientAddress").asText();

        SendingRecipient recipientEmail = Email
            .of(rawRecipientEmail)
            .orElseThrow(() -> new CustomDeserializationException(
                "Клиент передал некорректный email: " + rawRecipientEmail,
                "recipientAddress",
                p.currentLocation()
            ));

        String rawOtpReason = node.get("reason").asText();

        OtpReason otpReason = OtpReason
            .of(rawOtpReason)
            .orElseThrow(() -> new CustomDeserializationException(
                "Клиент передал некорректный тип OTP: " + rawOtpReason,
                "mailType",
                p.currentLocation()
            ));

        @NotBlank
        String subject = node.get("subject").asText();
        @NotBlank
        Short code = Short.valueOf(node.get("code").asText());
        JsonNode attachments = node.get("attachmentUrls");

        List<String> attachmentUrls = attachments != null
            ? attachments.valueStream()
            .map(JsonNode::asText)
            .filter(s -> !s.isBlank())
            .toList()
            : Collections.emptyList();

        return new SendOtpMailRequest(
            recipientEmail,
            subject,
            code,
            otpReason,
            attachmentUrls
        );
    }
}
