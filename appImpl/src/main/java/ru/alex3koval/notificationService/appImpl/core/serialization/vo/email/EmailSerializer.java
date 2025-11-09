package ru.alex3koval.notificationService.appImpl.core.serialization.vo.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.alex3koval.notificationService.domain.vo.Email;

import java.io.IOException;

public class EmailSerializer extends JsonSerializer<Email> {
    @Override
    public void serialize(Email value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }
}
