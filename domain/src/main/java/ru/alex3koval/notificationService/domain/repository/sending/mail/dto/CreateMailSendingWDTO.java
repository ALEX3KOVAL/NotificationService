package ru.alex3koval.notificationService.domain.repository.sending.mail.dto;

import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record CreateMailSendingWDTO(
    String subject,
    SendingRecipient recipient,
    SendingReason reason,
    MailFormat format,
    Map<String, Object> model,
    List<String> attachmentUrls,
    LocalDateTime createdAt
) {
}
