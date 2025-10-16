package ru.alex3koval.notificationService.appImpl.core.serialization.event.deser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemplatedMailSendingHasBeenRequestedEventDeserializer extends JsonDeserializer<TemplatedMailSendingHasBeenRequestedEvent> {
    @Override
    public TemplatedMailSendingHasBeenRequestedEvent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec oc = p.getCodec();

        JsonNode node = p.getCodec().readTree(p);

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

        return TemplatedMailSendingHasBeenRequestedEvent.of(
            sendingRecipient,
            node.get("subject").asText(),
            attachmentUrls,
            node.get("otpTemplateFolderPath").asText(),
            node.get("otpTemplateName").asText(),
            node.get("code").shortValue(),
            otpReason,
            sendingReason
        );
    }
}
