package ru.alex3koval.notificationService.domain.repository.sending.mail.dto;

import java.util.List;
import java.util.UUID;

public record MailSendingRDTO(
    UUID id,
    String recipientAddress,
    String subject,
    String text,
    List<String> attachmentUrls
) {
}
