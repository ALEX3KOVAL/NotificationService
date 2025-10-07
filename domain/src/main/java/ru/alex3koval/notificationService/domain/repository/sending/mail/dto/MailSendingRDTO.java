package ru.alex3koval.notificationService.domain.repository.sending.mail.dto;

import java.util.List;

public record MailSendingRDTO<T>(
    T id,
    String recipientAddress,
    String subject,
    String text,
    List<String> attachmentUrls
) {
}
