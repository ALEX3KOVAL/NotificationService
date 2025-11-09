package ru.alex3koval.notificationService.storage.repository.orm;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.storage.entity.TransactionalOutbox;

public interface OrmEventRepository<T> extends ReactiveCrudRepository<TransactionalOutbox<T>, T> {
    @Query(
        "INSERT INTO transactional_outbox (name, topic, status, json, created_at, updated_at) " +
        "VALUES (:#{#outbox.name}, :#{#outbox.topic}, :#{#outbox.status}, :#{#outbox.json}::jsonb, :#{#outbox.createdAt}, :#{#outbox.updatedAt}) " +
        "RETURNING *"
    )
    Mono<TransactionalOutbox<T>> saveWithReturning(
        @Param("outbox") TransactionalOutbox<T> outbox
    );
}
