package ru.alex3koval.notificationService.domain.repository.sending.mail.dto;

import ru.alex3koval.notificationService.domain.vo.Identifier;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

public record MailSendingRDTO<T>(
    T id,
    Identifier recipient,
    String subject,
    SendingReason reason,
    MailFormat format,
    String jsonModel
) {
}
