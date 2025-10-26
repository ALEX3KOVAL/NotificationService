package ru.alex3koval.notificationService.domain.repository.sending.mail.dto;

import ru.alex3koval.notificationService.domain.vo.Identifier;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

import java.time.LocalDateTime;
import java.util.Map;

public record CreateMailSendingWDTO(
    String subject,
    Identifier recipient,
    SendingReason reason,
    MailFormat format,
    Map<String, Object> model,
    LocalDateTime createdAt
) {
}
