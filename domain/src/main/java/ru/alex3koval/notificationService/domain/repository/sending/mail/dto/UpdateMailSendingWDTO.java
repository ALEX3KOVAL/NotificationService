package ru.alex3koval.notificationService.domain.repository.sending.mail.dto;

import ru.alex3koval.notificationService.domain.vo.SendingStatus;

public record UpdateMailSendingWDTO(
    SendingStatus status
) {
}
