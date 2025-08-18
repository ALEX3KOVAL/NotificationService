package ru.alex3koval.notificationService.server.exception;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;

@Getter
public class CustomDeserializationException extends JsonProcessingException {
    private final String fieldName;

    public CustomDeserializationException(String msg, String fieldName, JsonLocation loc) {
        super(msg, loc);

        this.fieldName = fieldName;
    }
}
