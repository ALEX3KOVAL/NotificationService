package ru.alex3koval.notificationService.appImpl.core.serialization.vo.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.io.IOException;

public class SendingRecipientSerializer extends JsonSerializer<SendingRecipient> {
    @Override
    public void serialize(SendingRecipient value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }
}
