package ru.alex3koval.notificationService.domain.repository.sending.phone.dto;

import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.Identifier;

public record PhoneSendingRDTO<T>(
    T id,
    Identifier recipient,
    SendingReason reason,
    String text
) {
}
