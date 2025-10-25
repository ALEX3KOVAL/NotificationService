package ru.alex3koval.notificationService.server.api.otp.dto.request.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import jakarta.validation.constraints.NotBlank;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.Identifier;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpMailRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class SendMailRequestDeserializer extends StdDeserializer<SendOtpMailRequest> {
    public SendMailRequestDeserializer() {
        super(SendOtpMailRequest.class);
    }

    @Override
    public SendOtpMailRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        MailFormat mailFormat = MailFormat.HTML;

        Identifier recipientEmail = ctxt.readValue(
            node.get("recipientAddress").traverse(oc),
            Identifier.class
        );

        OtpReason otpReason = ctxt.readValue(
            node.get("reason").traverse(oc),
            OtpReason.class
        );

        if (node.has("mailFormat")) {
            mailFormat = ctxt.readValue(
                node.get("mailFormat").traverse(oc),
                MailFormat.class
            );
        }

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
            attachmentUrls,
            mailFormat
        );
    }
}
