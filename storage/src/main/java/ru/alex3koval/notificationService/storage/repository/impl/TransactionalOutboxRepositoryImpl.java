package ru.alex3koval.notificationService.storage.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventingContract.dto.CreateEventWDTO;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.common.vo.Topic;
import ru.alex3koval.notificationService.storage.entity.TransactionalOutbox;
import ru.alex3koval.notificationService.storage.entity.base.BaseEntity;
import ru.alex3koval.notificationService.storage.repository.impl.dto.TransactionalOutboxRecordRDTO;
import ru.alex3koval.notificationService.storage.repository.orm.OrmEventRepository;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class TransactionalOutboxRepositoryImpl<T> {
    private final OrmEventRepository<T> repository;

    public Mono<TransactionalOutboxRecordRDTO> get(T id) {
        return repository
            .findById(id)
            .map(this::toRdto);
    }

    @SneakyThrows
    public Mono<T> add(CreateEventWDTO dto) {
        return repository
            .save(toEntity(dto))
            .map(BaseEntity::getId);
    }

    private TransactionalOutboxRecordRDTO toRdto(TransactionalOutbox<T> entity) {
        return new TransactionalOutboxRecordRDTO(
            entity.getName(),
            entity.getTopic(),
            entity.getJson(),
            entity.getStatus(),
            entity.getCreatedAt()
        );
    }

    private TransactionalOutbox<T> toEntity(CreateEventWDTO dto) throws DomainException {
        try {
            return new TransactionalOutbox<>(
                dto.name(),
                Topic.of(dto.topic()).get(),
                dto.status(),
                dto.json()
            );
        } catch(NoSuchElementException exc) {
            throw new DomainException(String.format("Топик не найден: %s", dto.topic()));
        }
    }
}
