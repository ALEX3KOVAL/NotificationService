package ru.alex3koval.notificationService.domain.repository.sending.mail.dto;

import ru.alex3koval.notificationService.domain.vo.SendingStatus;

import java.util.List;

public record CreateMailSendingWDTO(
    String subject,
    String text,
    SendingStatus status,
    List<String> attachmentUrls
) {
}
