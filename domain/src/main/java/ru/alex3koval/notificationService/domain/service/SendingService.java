package ru.alex3koval.notificationService.domain.service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.repository.base.SendingRepository;

@RequiredArgsConstructor
public abstract class SendingService<ID, CREATION_DTO, UPDATING_DTO, RDTO> {
    private final SendingRepository<ID, CREATION_DTO, UPDATING_DTO, RDTO> repository;

    public Mono<ID> create(CREATION_DTO dto) throws DomainException {
        return repository.create(dto);
    }
}
