package ru.alex3koval.notificationService.appImpl.core.serialization.vo.topic;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.alex3koval.notificationService.domain.common.vo.Topic;

import java.io.IOException;

public class TopicDeserializer extends JsonDeserializer<Topic> {
    @Override
    public Topic deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken() != JsonToken.VALUE_STRING) {
            p.nextToken();
        }
        String rawTopic = p.getText();

        return Topic
            .of(rawTopic)
            .orElseThrow(() ->
                new RuntimeException(String.format("Не удалось десериализовать из: %s", rawTopic))
            );
    }
}
