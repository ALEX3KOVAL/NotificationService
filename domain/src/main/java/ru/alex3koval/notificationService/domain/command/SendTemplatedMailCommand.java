package ru.alex3koval.notificationService.domain.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.util.List;
import java.util.Map;

public abstract class SendTemplatedMailCommand<T> extends SendMailCommand<T> {
    protected final String templateFolderPath;
    protected final String templateFileName;
    protected final Map<String, Object> model;

    protected SendTemplatedMailCommand(
        DTO dto,
        MailerService<T> mailerService
    ) {
        super(
            dto.recipientAddress,
            dto.subject,
            dto.attachmentUrls,
            mailerService
        );

        this.templateFolderPath = dto.templateFolderPath;
        this.templateFileName = dto.templateFileName;
        this.model = null;
    }

    @RequiredArgsConstructor
    @Getter
    public static class DTO {
        private final SendingRecipient recipientAddress;
        private final String subject;
        private final List<String> attachmentUrls;
        private final String templateFolderPath;
        private final String templateFileName;
        private final String code;
        private final OtpReason otpReason;

        @Setter
        private Map<String, Object> model;
    }
}
