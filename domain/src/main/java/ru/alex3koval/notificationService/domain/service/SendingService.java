package ru.alex3koval.notificationService.domain.service;

import reactor.core.publisher.Mono;

public interface SendingService<ID, CREATION_DTO, UPDATING_DTO> {
    Mono<ID> create(CREATION_DTO dto);

    Mono<ID> update(
        ID id,
        UPDATING_DTO dto
    );
}
