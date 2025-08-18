package ru.alex3koval.notificationService.domain.repository.event.dto;

import java.util.Optional;

public record UpdateEventWDTO(
    Optional<String> name
) {
}
