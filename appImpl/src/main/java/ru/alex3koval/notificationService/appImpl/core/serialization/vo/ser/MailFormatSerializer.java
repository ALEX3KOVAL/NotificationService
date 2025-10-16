package ru.alex3koval.notificationService.appImpl.core.serialization.vo.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.alex3koval.notificationService.domain.vo.MailFormat;

import java.io.IOException;

public class MailFormatSerializer extends JsonSerializer<MailFormat> {
    @Override
    public void serialize(MailFormat mailFormat, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(mailFormat.getValue());
    }
}
