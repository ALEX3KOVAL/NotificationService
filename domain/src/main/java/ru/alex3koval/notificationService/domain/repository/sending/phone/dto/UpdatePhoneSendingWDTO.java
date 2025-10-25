package ru.alex3koval.notificationService.domain.repository.sending.phone.dto;

import ru.alex3koval.notificationService.domain.vo.Identifier;

public record UpdatePhoneSendingWDTO(
    Identifier recipient
) {
}
