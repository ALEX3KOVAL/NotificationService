package ru.alex3koval.notificationService.domain.common.repository.dto;

import ru.alex3koval.eventingContract.vo.EventStatus;
import ru.alex3koval.notificationService.domain.common.vo.Topic;

import java.time.LocalDateTime;

public record EventRDTO(
    String name,
    Topic topic,
    String json,
    EventStatus status,
    LocalDateTime createdAt
) {
}
