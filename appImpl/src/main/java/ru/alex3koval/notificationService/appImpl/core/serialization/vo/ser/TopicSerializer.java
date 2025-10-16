package ru.alex3koval.notificationService.appImpl.core.serialization.vo.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.alex3koval.notificationService.domain.common.vo.Topic;

import java.io.IOException;

public class TopicSerializer extends JsonSerializer<Topic> {
    @Override
    public void serialize(Topic value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }
}
