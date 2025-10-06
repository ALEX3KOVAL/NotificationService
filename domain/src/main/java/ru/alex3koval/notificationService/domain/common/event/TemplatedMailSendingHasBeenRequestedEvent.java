package ru.alex3koval.notificationService.domain.common.event;

import lombok.Getter;
import ru.alex3koval.notificationService.domain.command.SendTemplatedMailCommand;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.util.List;

@Getter
public class TemplatedMailSendingHasBeenRequestedEvent {
    private final SendingRecipient recipientAddress;
    private final String subject;
    private final List<String> attachmentUrls;
    private final String otpTemplateFolderPath;
    private final String otpTemplateName;
    private final String code;
    private final OtpReason otpReason;

    public TemplatedMailSendingHasBeenRequestedEvent(
        SendTemplatedMailCommand.DTO dto
    ) {
        this.recipientAddress = dto.getRecipientAddress();
        this.subject = dto.getSubject();
        this.attachmentUrls = dto.getAttachmentUrls();
        this.otpTemplateFolderPath = dto.getTemplateFolderPath();
        this.otpTemplateName = dto.getTemplateFileName();
        this.code = dto.getCode();
        this.otpReason = dto.getOtpReason();
    }
}
