package ru.alex3koval.notificationService.server.exceptionHandler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.server.exception.CustomDeserializationException;

import java.time.Instant;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(CustomDeserializationException.class)
    public Mono<ServerResponse> handleCustomDeserializationException(
        CustomDeserializationException ex
    ) {
        return ServerResponse
            .badRequest()
            .bodyValue(
                new ErrorDeserializationResponse(ex.getMessage(), ex.getFieldName())
            );
    }

    @ExceptionHandler(RuntimeException.class)
    public Mono<ServerResponse> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Getter
    private static class ErrorDeserializationResponse {
        final String message;
        final String fieldName;
        final Instant timestamp;

        public ErrorDeserializationResponse(String message, String fieldName) {
            this.message = message;
            this.fieldName = fieldName;
            this.timestamp = Instant.now();
        }
    }
}
