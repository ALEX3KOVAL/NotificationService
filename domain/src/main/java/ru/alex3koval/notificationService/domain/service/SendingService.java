package ru.alex3koval.notificationService.domain.service;

import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;

public interface SendingService<ID, CREATION_DTO, UPDATING_DTO> {
    Mono<ID> createSending(CREATION_DTO dto) throws DomainException;

    Mono<ID> update(
        ID id,
        UPDATING_DTO dto
    );
}
