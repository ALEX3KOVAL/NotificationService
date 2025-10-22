package ru.alex3koval.notificationService.appImpl.core.serialization.event.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplatedMailSendingHasBeenRequestedEventDeserializer extends JsonDeserializer<TemplatedMailSendingHasBeenRequestedEvent> {
    @Override
    public TemplatedMailSendingHasBeenRequestedEvent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);

        SendingRecipient sendingRecipient = ctxt.readValue(
            node.get("recipientAddress").traverse(oc),
            SendingRecipient.class
        );

        List<String> attachmentUrls = new ArrayList<>();

        if (node.has("attachmentUrls")) {
            JsonNode urlsNode = node.get("attachmentUrls");
            if (urlsNode.isArray()) {
                for (JsonNode urlNode : urlsNode) {
                    attachmentUrls.add(urlNode.asText());
                }
            }
        }

        SendingReason sendingReason = ctxt.readValue(
            node.get("sendingReason").traverse(oc),
            SendingReason.class
        );

        OtpReason otpReason = ctxt.readValue(
            node.get("otpReason").traverse(oc),
            OtpReason.class
        );

        MailFormat mailFormat = ctxt.readValue(
            node.get("mailFormat").traverse(oc),
            MailFormat.class
        );

        Map<String, Object> model = ((ObjectMapper) oc)
            .readValue(
                node.get("model").traverse(oc),
                new TypeReference<>() {}
            );

        return TemplatedMailSendingHasBeenRequestedEvent.of(
            sendingRecipient,
            node.get("subject").asText(),
            attachmentUrls,
            node.get("otpTemplateFolderPath").asText(),
            node.get("otpTemplateName").asText(),
            node.get("code").shortValue(),
            otpReason,
            sendingReason,
            mailFormat,
            model
        );
    }
}
