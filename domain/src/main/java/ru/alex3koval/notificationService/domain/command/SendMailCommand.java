package ru.alex3koval.notificationService.domain.command;

import lombok.Getter;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

public abstract class SendMailCommand<T> implements Command<Mono<T>> {
    protected final SendingRecipient recipientAddress;
    protected final String subject;
    protected final List<String> attachmentUrls;
    protected final MailerService<T> mailerService;
    private final SendingReason reason;
    private final MailFormat format;
    protected final Map<String, Object> model;

    protected SendMailCommand(
        SendingRecipient recipientAddress,
        String subject,
        SendingReason reason,
        List<String> attachmentUrls,
        MailerService<T> mailerService,
        MailFormat format,
        Map<String, Object> model
    ) {
        this.mailerService = mailerService;
        this.recipientAddress = recipientAddress;
        this.subject = subject;
        this.attachmentUrls = attachmentUrls;
        this.format = format;
        this.model = model;
        this.reason = reason;
    }

    protected Mono<T> sendMessage(String messageText) {
        return mailerService
            .send(
                recipientAddress.getValue(),
                subject,
                messageText,
                format
            )
            .then(
                mailerService.create(
                    new CreateMailSendingWDTO(
                        subject,
                        recipientAddress,
                        reason,
                        format,
                        model,
                        attachmentUrls,
                        LocalDateTime.now(ZoneOffset.UTC)
                    )
                )
            );
    }

    @Getter
    public static class DTO {
        private final SendingRecipient recipientAddress;
        private final String subject;
        private final List<String> attachmentUrls;
        private final SendingReason reason;
        private OtpReason otpReason;
        private final Map<String, Object> model;
        private final MailFormat format;

        protected DTO(
            SendingRecipient recipientAddress,
            String subject,
            List<String> attachmentUrls,
            SendingReason reason,
            MailFormat format,
            Map<String, Object> model
        ) {
            this.recipientAddress = recipientAddress;
            this.subject = subject;
            this.attachmentUrls = attachmentUrls;
            this.reason = reason;
            this.format = format;
            this.model = model;
        }

        protected DTO(
            SendingRecipient recipientAddress,
            String subject,
            List<String> attachmentUrls,
            SendingReason reason,
            OtpReason otpReason,
            MailFormat format,
            Map<String, Object> model
        ) {
            this.recipientAddress = recipientAddress;
            this.subject = subject;
            this.attachmentUrls = attachmentUrls;
            this.reason = reason;
            this.otpReason = otpReason;
            this.format = format;
            this.model = model;
        }
    }
}
