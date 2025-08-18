package ru.alex3koval.notificationService.domain.repository.base;

import reactor.core.publisher.Mono;

public interface BaseRepository<ID, RDTO, CREATION_DTO, UPDATING_DTO> {
    Mono<RDTO> get(ID id);

    Mono<ID> create(CREATION_DTO dto);

    Mono<ID> update(ID id, UPDATING_DTO dto);
}
