package ru.alex3koval.notificationService.domain.common.event;

import lombok.Getter;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.Identifier;

import java.util.List;
import java.util.Map;

@Getter
public class TemplatedMailSendingHasBeenRequestedEvent {
    private final Identifier recipientAddress;
    private final String subject;
    private final List<String> attachmentUrls;
    private final String otpTemplateFolderPath;
    private final String otpTemplateName;
    private final MailFormat mailFormat;
    private final SendingReason sendingReason;
    private final Map<String, Object> model;
    private OtpReason otpReason;
    private Short code;

    private TemplatedMailSendingHasBeenRequestedEvent(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName,
        SendingReason sendingReason,
        MailFormat mailFormat,
        Map<String, Object> model
    ) {
        this.recipientAddress = recipientAddress;
        this.subject = subject;
        this.attachmentUrls = attachmentUrls;
        this.otpTemplateFolderPath = otpTemplateFolderPath;
        this.otpTemplateName = otpTemplateName;
        this.sendingReason = sendingReason;
        this.mailFormat = mailFormat;
        this.model = model;
    }

    private TemplatedMailSendingHasBeenRequestedEvent(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName,
        Short code,
        SendingReason sendingReason,
        OtpReason otpReason,
        MailFormat mailFormat,
        Map<String, Object> model
    ) {
        this(
            recipientAddress,
            subject,
            attachmentUrls,
            otpTemplateFolderPath,
            otpTemplateName,
            sendingReason,
            mailFormat,
            model
        );
        this.otpReason = otpReason;
        this.code = code;
    }

    public static TemplatedMailSendingHasBeenRequestedEvent ofBase(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName,
        MailFormat mailFormat,
        Map<String, Object> model
    ) {
        return new TemplatedMailSendingHasBeenRequestedEvent(
            recipientAddress,
            subject,
            attachmentUrls,
            otpTemplateFolderPath,
            otpTemplateName,
            SendingReason.OTHER,
            mailFormat,
            model
        );
    }

    public static TemplatedMailSendingHasBeenRequestedEvent of(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName,
        Short code,
        OtpReason otpReason,
        SendingReason sendingReason,
        MailFormat mailFormat,
        Map<String, Object> model
    ) {
        if (sendingReason.isOTP()) {
            return ofOTP(
                recipientAddress,
                subject,
                attachmentUrls,
                otpTemplateFolderPath,
                otpTemplateName,
                code,
                otpReason,
                mailFormat,
                model
            );
        }

        return ofBase(
            recipientAddress,
            subject,
            attachmentUrls,
            otpTemplateFolderPath,
            otpTemplateName,
            mailFormat,
            model
        );
    }

    public static TemplatedMailSendingHasBeenRequestedEvent ofOTP(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String otpTemplateFolderPath,
        String otpTemplateName,
        Short code,
        OtpReason otpReason,
        MailFormat mailFormat,
        Map<String, Object> model
    ) {
        return new TemplatedMailSendingHasBeenRequestedEvent(
            recipientAddress,
            subject,
            attachmentUrls,
            otpTemplateFolderPath,
            otpTemplateName,
            code,
            SendingReason.OTP,
            otpReason,
            mailFormat,
            model
        );
    }
}
