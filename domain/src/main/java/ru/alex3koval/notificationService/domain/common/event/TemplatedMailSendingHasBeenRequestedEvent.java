package ru.alex3koval.notificationService.domain.common.event;

import lombok.Getter;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.util.List;

@Getter
public class TemplatedMailSendingHasBeenRequestedEvent {
    private final SendingRecipient recipientAddress;
    private final String subject;
    private final List<String> attachmentUrls;
    private final String otpTemplateFolderPath;
    private final String otpTemplateName;
    private final SendingReason sendingReason;
    private OtpReason otpReason;
    private Short code;

    private TemplatedMailSendingHasBeenRequestedEvent(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName,
        SendingReason sendingReason
    ) {
        this.recipientAddress = recipientAddress;
        this.subject = subject;
        this.attachmentUrls = attachmentUrls;
        this.otpTemplateFolderPath = otpTemplateFolderPath;
        this.otpTemplateName = otpTemplateName;
        this.sendingReason = sendingReason;
    }

    private TemplatedMailSendingHasBeenRequestedEvent(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName,
        Short code,
        SendingReason sendingReason,
        OtpReason otpReason
    ) {
        this(
            recipientAddress,
            subject,
            attachmentUrls,
            otpTemplateFolderPath,
            otpTemplateName,
            sendingReason
        );
        this.otpReason = otpReason;
        this.code = code;
    }

    public static TemplatedMailSendingHasBeenRequestedEvent ofBase(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName
    ) {
        return new TemplatedMailSendingHasBeenRequestedEvent(
            recipientAddress,
            subject,
            attachmentUrls,
            otpTemplateFolderPath,
            otpTemplateName,
            SendingReason.OTP
        );
    }

    public static TemplatedMailSendingHasBeenRequestedEvent of(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName,
        Short code,
        OtpReason otpReason,
        SendingReason sendingReason
    ) {
        if (sendingReason.isOTP()) {
            return ofOTP(
                recipientAddress,
                subject,
                attachmentUrls,
                otpTemplateFolderPath,
                otpTemplateName,
                code,
                otpReason
            );
        }

        return ofBase(
            recipientAddress,
            subject,
            attachmentUrls,
            otpTemplateFolderPath,
            otpTemplateName
        );
    }

    public static TemplatedMailSendingHasBeenRequestedEvent ofOTP(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName,
        Short code,
        OtpReason otpReason
    ) {
        return new TemplatedMailSendingHasBeenRequestedEvent(
            recipientAddress,
            subject,
            attachmentUrls,
            otpTemplateFolderPath,
            otpTemplateName,
            code,
            SendingReason.OTP,
            otpReason
        );
    }
}
