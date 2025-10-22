package ru.alex3koval.notificationService.appImpl.core.serialization.event.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;

import java.io.IOException;

public class TemplatedMailSendingHasBeenRequestedEventSerializer extends JsonSerializer<TemplatedMailSendingHasBeenRequestedEvent> {
    @Override
    public void serialize(TemplatedMailSendingHasBeenRequestedEvent value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeObjectField("recipientAddress", value.getRecipientAddress());
        gen.writeObjectField("subject", value.getSubject());

        gen.writeArrayFieldStart("attachmentUrls");
        for (String url : value.getAttachmentUrls()) {
            gen.writeString(url);
        }
        gen.writeEndArray();

        gen.writeObjectField("mailFormat", value.getMailFormat());

        gen.writeStringField("otpTemplateFolderPath", value.getOtpTemplateFolderPath());
        gen.writeStringField("otpTemplateName", value.getOtpTemplateName());

        gen.writeObjectField("sendingReason", value.getSendingReason());
        gen.writeObjectField("otpReason", value.getOtpReason());
        gen.writeNumberField("code", value.getCode());

        gen.writeObjectField("model", value.getModel());

        gen.writeEndObject();
    }
}
