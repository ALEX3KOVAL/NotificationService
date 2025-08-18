package ru.alex3koval.notificationService.server.api.otp.dto.request;

import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

public record SendOtpViaPhoneRequest(
    SendingRecipient phone,
    String text
) {
}
