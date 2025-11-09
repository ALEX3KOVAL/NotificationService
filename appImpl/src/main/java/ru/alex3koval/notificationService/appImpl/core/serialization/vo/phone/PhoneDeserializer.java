package ru.alex3koval.notificationService.appImpl.core.serialization.vo.phone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.alex3koval.notificationService.domain.vo.Phone;

import java.io.IOException;

public class PhoneDeserializer extends JsonDeserializer<Phone> {
    @Override
    public Phone deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken() != JsonToken.VALUE_STRING) {
            p.nextToken();
        }
        String rawPhone = p.getText();

        return (Phone) Phone
            .of(rawPhone)
            .orElseThrow(() -> new RuntimeException(String.format("Не удалось десериализовать из: %s", rawPhone)));
    }
}
