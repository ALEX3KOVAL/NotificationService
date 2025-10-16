package ru.alex3koval.notificationService.appImpl.core.serialization.vo.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.alex3koval.notificationService.domain.vo.Phone;

import java.io.IOException;

public class PhoneSerializer extends JsonSerializer<Phone> {
    @Override
    public void serialize(Phone phone, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(phone.getValue());
    }
}
