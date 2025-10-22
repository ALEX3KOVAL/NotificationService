package ru.alex3koval.notificationService.domain.command;

import lombok.Getter;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.util.List;
import java.util.Map;

public abstract class SendTemplatedMailCommand<T> extends SendMailCommand<T> {
    protected final String templateFolderPath;
    protected final String templateFileName;

    protected SendTemplatedMailCommand(
        DTO dto,
        MailerService<T> mailerService
    ) {
        super(
            dto.getRecipientAddress(),
            dto.getSubject(),
            dto.getReason(),
            dto.getAttachmentUrls(),
            mailerService,
            dto.getFormat(),
            dto.getModel()
        );

        this.templateFolderPath = dto.getTemplateFolderPath();
        this.templateFileName = dto.getTemplateFileName();
    }

    @Getter
    public static class DTO extends SendMailCommand.DTO {
        private final String templateFolderPath;
        private final String templateFileName;

        public DTO(
            SendingRecipient recipientAddress,
            String subject,
            List<String> attachmentUrls,
            String templateFolderPath,
            String templateFileName,
            MailFormat format,
            SendingReason reason,
            Map<String, Object> model
        ) {
            super(recipientAddress, subject, attachmentUrls, reason, format, model);

            this.templateFolderPath = templateFolderPath;
            this.templateFileName = templateFileName;
        }

        public static DTO ofBase(
            SendingRecipient recipientAddress,
            String subject,
            List<String> attachmentUrls,
            String templateFolderPath,
            String templateFileName,
            String text,
            SendingReason reason
        ) {
            return new DTO(
                recipientAddress,
                subject,
                attachmentUrls,
                templateFolderPath,
                templateFileName,
                MailFormat.TEXT,
                reason,
                Map.of("text", text)
            );
        }

        public static DTO ofOTP(
            SendingRecipient recipientAddress,
            String subject,
            List<String> attachmentUrls,
            String templateFolderPath,
            String templateFileName,
            Short code,
            OtpReason otpReason
        ) {
            return new DTO(
                recipientAddress,
                subject,
                attachmentUrls,
                templateFolderPath,
                templateFileName,
                MailFormat.HTML,
                SendingReason.OTP,
                Map.of(
                    "code", code,
                    "reason", otpReason.toString()
                )
            );
        }
    }
}
