package ru.alex3koval.notificationService.storage.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventingContract.dto.CreateEventWDTO;
import ru.alex3koval.eventingContract.dto.UpdateEventWDTO;
import ru.alex3koval.eventingContract.vo.EventStatus;
import ru.alex3koval.notificationService.domain.common.repository.EventRepository;
import ru.alex3koval.notificationService.domain.common.repository.dto.EventRDTO;
import ru.alex3koval.notificationService.domain.common.vo.Topic;
import ru.alex3koval.notificationService.storage.entity.TransactionalOutbox;
import ru.alex3koval.notificationService.storage.repository.orm.OrmEventRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class TransactionalOutboxRepositoryImpl<T> implements EventRepository<T> {
    private final OrmEventRepository<T> repository;
    private final R2dbcEntityTemplate template;

    public Mono<EventRDTO> get(T id) {
        return repository
            .findById(id)
            .map(this::toRdto);
    }

    public Mono<T> add(CreateEventWDTO dto) {
        return repository
            .saveWithReturning(toEntity(dto))
            .map(TransactionalOutbox::getId);
    }

    @Override
    public Mono<T> update(T id, UpdateEventWDTO dto) {
        Map<SqlIdentifier, Object> fieldsForUpdating = new HashMap<>(Map.of());

        if (dto.status() != null) {
            fieldsForUpdating.put(SqlIdentifier.quoted("status"), dto.status());
        }

        if (fieldsForUpdating.isEmpty()) {
            return Mono.just(id);
        }

        fieldsForUpdating.put(
            SqlIdentifier.quoted("updated_at"),
            LocalDateTime.now(ZoneOffset.UTC)
        );

        Query query = Query.query(
            Criteria.where("id").is(id)
        );

        return template
            .update(TransactionalOutbox.class)
            .matching(query)
            .apply(Update.from(fieldsForUpdating))
            .thenReturn(id);
    }

    @Override
    public Mono<T> updateStatus(T id, EventStatus newStatus) {
        return update(
            id,
            new UpdateEventWDTO(newStatus)
        );
    }

    private TransactionalOutbox<T> toEntity(CreateEventWDTO dto) {
        return new TransactionalOutbox<>(
            dto.name(),
            dto.topic(),
            dto.status(),
            dto.json(),
            dto.createdAt(),
            dto.createdAt()
        );
    }

    private EventRDTO toRdto(TransactionalOutbox<T> entity) {
        return new EventRDTO(
            entity.getName(),
            Topic
                .of(entity.getTopic())
                .orElseThrow(() -> new RuntimeException("Не найден топик: " + entity.getTopic())),
            entity.getJson(),
            entity.getStatus(),
            entity.getCreatedAt()
        );
    }
}
