package ru.alex3koval.notificationService.storage.repository.impl;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventingContract.dto.CreateEventWDTO;
import ru.alex3koval.notificationService.domain.common.vo.Topic;
import ru.alex3koval.notificationService.storage.entity.TransactionalOutbox;
import ru.alex3koval.notificationService.storage.repository.impl.dto.TransactionalOutboxRecordRDTO;
import ru.alex3koval.notificationService.storage.repository.orm.OrmEventRepository;

@RequiredArgsConstructor
public class TransactionalOutboxRepositoryImpl<T> {
    private final OrmEventRepository<T> repository;

    public Mono<TransactionalOutboxRecordRDTO> get(T id) {
        return repository
            .findById(id)
            .map(this::toRdto);
    }

    public Mono<T> add(CreateEventWDTO dto) {
        System.out.println(dto);

        return repository
            .saveWithReturning(toEntity(dto))
            .map(TransactionalOutbox::getId);
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

    private TransactionalOutboxRecordRDTO toRdto(TransactionalOutbox<T> entity) {
        return new TransactionalOutboxRecordRDTO(
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
