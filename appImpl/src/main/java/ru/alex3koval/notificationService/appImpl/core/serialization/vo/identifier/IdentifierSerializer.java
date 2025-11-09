package ru.alex3koval.notificationService.appImpl.core.serialization.vo.identifier;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.alex3koval.notificationService.domain.vo.Identifier;

import java.io.IOException;

public class IdentifierSerializer extends JsonSerializer<Identifier> {
    @Override
    public void serialize(Identifier value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }
}
