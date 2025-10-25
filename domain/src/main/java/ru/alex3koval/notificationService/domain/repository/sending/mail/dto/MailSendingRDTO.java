package ru.alex3koval.notificationService.domain.repository.sending.mail.dto;

import ru.alex3koval.notificationService.domain.vo.Identifier;

public record MailSendingRDTO<T>(
    T id,
    Identifier recipientAddress,
    String subject
) {
}
