package ru.alex3koval.notificationService.appImpl.core.serialization.vo.otpReason;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.alex3koval.notificationService.domain.vo.OtpReason;

import java.io.IOException;

public class OtpReasonSerializer extends JsonSerializer<OtpReason> {
    @Override
    public void serialize(OtpReason otpReason, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(otpReason.name());
    }
}
