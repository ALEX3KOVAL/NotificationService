package ru.alex3koval.notificationService.server.api.otp.dto.request;

import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.Phone;

public record SendOtpViaPhoneRequest(
    Phone phone,
    OtpReason reason,
    String text
) {
}
