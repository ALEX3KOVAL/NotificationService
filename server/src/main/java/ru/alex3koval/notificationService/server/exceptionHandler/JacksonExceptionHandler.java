package ru.alex3koval.notificationService.server.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.alex3koval.notificationService.server.exception.CustomDeserializationException;

import java.time.Instant;

@ControllerAdvice
public class JacksonExceptionHandler {
    @ExceptionHandler(CustomDeserializationException.class)
    public ResponseEntity<ErrorResponse> handleCustomDeserializationException(
        CustomDeserializationException ex
    ) {
        return ResponseEntity.badRequest().body(
            new ErrorResponse(ex.getMessage(), ex.getFieldName())
        );
    }

    public record ErrorResponse(
        String message,
        String fieldName,
        Instant timestamp
    ) {
        public ErrorResponse(String message, String fieldName) {
            this(message, fieldName, Instant.now());
        }
    }

}
