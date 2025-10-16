package ru.alex3koval.notificationService.domain.repository.sending.mail.dto;

import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

public record MailSendingRDTO<T>(
    T id,
    SendingRecipient recipientAddress,
    String subject
) {
}
