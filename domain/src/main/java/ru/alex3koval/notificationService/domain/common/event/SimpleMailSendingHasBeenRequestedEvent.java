package ru.alex3koval.notificationService.domain.common.event;

import lombok.Getter;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.Identifier;

import java.util.List;

@Getter
public class SimpleMailSendingHasBeenRequestedEvent {
    private final Identifier recipientAddress;
    private final String subject;
    private final List<String> attachmentUrls;
    private final MailFormat mailFormat;
    private final SendingReason sendingReason;
    private final String text;
    private OtpReason otpReason;

    private SimpleMailSendingHasBeenRequestedEvent(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        SendingReason sendingReason,
        String text
    ) {
        this.recipientAddress = recipientAddress;
        this.subject = subject;
        this.attachmentUrls = attachmentUrls;
        this.sendingReason = sendingReason;
        this.mailFormat = MailFormat.TEXT;
        this.text = text;
    }

    private SimpleMailSendingHasBeenRequestedEvent(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        SendingReason sendingReason,
        OtpReason otpReason,
        String text
    ) {
        this(
            recipientAddress,
            subject,
            attachmentUrls,
            sendingReason,
            text
        );
        this.otpReason = otpReason;
    }

    public static SimpleMailSendingHasBeenRequestedEvent ofBase(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String text
    ) {
        return new SimpleMailSendingHasBeenRequestedEvent(
            recipientAddress,
            subject,
            attachmentUrls,
            SendingReason.OTHER,
            text
        );
    }

    public static SimpleMailSendingHasBeenRequestedEvent of(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        OtpReason otpReason,
        SendingReason sendingReason,
        String text
    ) {
        if (sendingReason.isOTP()) {
            return ofOTP(
                recipientAddress,
                subject,
                attachmentUrls,
                otpReason,
                text
            );
        }

        return ofBase(
            recipientAddress,
            subject,
            attachmentUrls,
            text
        );
    }

    public static SimpleMailSendingHasBeenRequestedEvent ofOTP(
        Identifier recipientAddress,
        String subject,
        List<String> attachmentUrls,
        OtpReason otpReason,
        String text
    ) {
        return new SimpleMailSendingHasBeenRequestedEvent(
            recipientAddress,
            subject,
            attachmentUrls,
            SendingReason.OTP,
            otpReason,
            text
        );
    }
}
