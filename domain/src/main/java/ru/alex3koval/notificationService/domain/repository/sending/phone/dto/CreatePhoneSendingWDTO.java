package ru.alex3koval.notificationService.domain.repository.sending.phone.dto;

import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.time.LocalDateTime;

public record CreatePhoneSendingWDTO(
    SendingRecipient recipient,
    SendingReason reason,
    String text,
    LocalDateTime createdAt
) {
}
