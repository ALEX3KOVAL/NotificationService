package ru.alex3koval.notificationService.storage.repository.orm;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.storage.entity.sending.EmailSending;

public interface OrmEmailSendingRepository<T> extends ReactiveCrudRepository<EmailSending<T>, T> {
    @Query("INSERT INTO email_sending (recipient, subject, model, reason, format, created_at, updated_at) VALUES (:#{#sending.recipient}, :#{#sending.subject}, :#{#sending.jsonModel}::jsonb, :#{#sending.reason}, :#{#sending.format}, :#{#sending.createdAt}, :#{#sending.updatedAt}) RETURNING *")
    Mono<EmailSending<T>> saveWithReturning(
        @Param("sending") EmailSending<T> sending
    );
}
