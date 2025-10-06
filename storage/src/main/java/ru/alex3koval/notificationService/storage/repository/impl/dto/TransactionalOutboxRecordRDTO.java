package ru.alex3koval.notificationService.storage.repository.impl.dto;

import ru.alex3koval.eventingContract.vo.EventStatus;
import ru.alex3koval.notificationService.domain.common.vo.Topic;

import java.time.LocalDateTime;

public record TransactionalOutboxRecordRDTO(
    String name,
    Topic topic,
    String json,
    EventStatus status,
    LocalDateTime createdAt
) {
}
