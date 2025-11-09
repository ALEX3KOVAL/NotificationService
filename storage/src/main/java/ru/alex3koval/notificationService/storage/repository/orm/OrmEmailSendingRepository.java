package ru.alex3koval.notificationService.storage.repository.orm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.storage.entity.sending.EmailSending;

public interface OrmEmailSendingRepository<T> extends ReactiveCrudRepository<EmailSending<T>, T> {
    @Query("INSERT INTO email_sending (recipient, subject, model, reason, format, created_at, updated_at) VALUES (:#{#sending.recipient}, :#{#sending.subject}, :#{#sending.jsonModel}::jsonb, :#{#sending.reason}, :#{#sending.format}, :#{#sending.createdAt}, :#{#sending.updatedAt}) RETURNING *")
    Mono<EmailSending<T>> saveWithReturning(
        @Param("sending") EmailSending<T> sending
    );

    default EmailSending<T> toEntity(CreateMailSendingWDTO dto, ObjectMapper objectMapper) throws JsonProcessingException {
        return new EmailSending<>(
            dto.subject(),
            dto.recipient(),
            dto.reason(),
            dto.format(),
            objectMapper.writeValueAsString(dto.model()),
            dto.createdAt(),
            dto.updatedAt()
        );
    }
}
