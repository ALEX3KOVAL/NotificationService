package ru.alex3koval.notificationService.domain.repository.sending.phone.dto;

import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

public record UpdatePhoneSendingWDTO(
    SendingRecipient recipient
) {
}
