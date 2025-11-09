package ru.alex3koval.notificationService.appImpl.core.serialization.vo.sendingReason;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

import java.io.IOException;

public class SendingReasonSerializer extends JsonSerializer<SendingReason> {
    @Override
    public void serialize(SendingReason sendingReason, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(sendingReason.getValue());
    }
}
