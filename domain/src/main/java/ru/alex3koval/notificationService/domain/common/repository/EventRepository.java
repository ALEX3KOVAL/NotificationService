package ru.alex3koval.notificationService.domain.common.repository;

import reactor.core.publisher.Mono;
import ru.alex3koval.eventingContract.dto.CreateEventWDTO;
import ru.alex3koval.eventingContract.dto.UpdateEventWDTO;
import ru.alex3koval.eventingContract.vo.EventStatus;
import ru.alex3koval.notificationService.domain.common.repository.dto.EventRDTO;

public interface EventRepository<T> {
    Mono<EventRDTO> get(T id);
    Mono<T> add(CreateEventWDTO dto);
    Mono<T> update(T id, UpdateEventWDTO dto);
    Mono<T> updateStatus(T id, EventStatus newStatus);
}
