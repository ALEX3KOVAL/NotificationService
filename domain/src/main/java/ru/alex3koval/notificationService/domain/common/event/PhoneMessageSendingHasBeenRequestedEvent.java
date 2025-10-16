package ru.alex3koval.notificationService.domain.common.event;

import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.Phone;

public record PhoneMessageSendingHasBeenRequestedEvent(
    Phone phone,
    OtpReason reason,
    String subject
) {
}
