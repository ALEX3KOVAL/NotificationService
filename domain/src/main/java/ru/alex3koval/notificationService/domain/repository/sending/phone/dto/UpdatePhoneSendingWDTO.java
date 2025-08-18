package ru.alex3koval.notificationService.domain.repository.sending.phone.dto;

import ru.alex3koval.notificationService.domain.vo.SendingStatus;

public record UpdatePhoneSendingWDTO(
    SendingStatus status
) {
}
