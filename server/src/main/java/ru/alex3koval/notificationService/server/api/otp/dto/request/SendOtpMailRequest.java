package ru.alex3koval.notificationService.server.api.otp.dto.request;

import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.util.List;

public record SendOtpMailRequest(
    SendingRecipient recipientAddress,
    String subject,
    Short code,
    OtpReason otpReason,
    List<String> attachmentUrls,
    MailFormat mailFormat
) {
}
