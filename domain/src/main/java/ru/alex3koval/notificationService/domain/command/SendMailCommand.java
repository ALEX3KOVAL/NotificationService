package ru.alex3koval.notificationService.domain.command;

import lombok.Getter;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.entity.Mail;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.service.FileServiceFacade;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.Identifier;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

public abstract class SendMailCommand<T> implements Command<Mono<T>> {
    protected final Identifier recipientAddress;
    protected final String subject;
    protected final MailerService<T> mailerService;
    protected final FileServiceFacade fileServiceFacade;
    private final SendingReason reason;
    private final MailFormat format;
    protected final Map<String, Object> model;

    @Getter
    public static class DTO {
        private final Identifier recipientAddress;
        private final String subject;
        private final SendingReason reason;
        private final Map<String, Object> model;
        private final MailFormat format;

        protected DTO(
            Identifier recipientAddress,
            String subject,
            SendingReason reason,
            MailFormat format,
            Map<String, Object> model
        ) {
            this.recipientAddress = recipientAddress;
            this.subject = subject;
            this.reason = reason;
            this.format = format;
            this.model = model;
        }
    }

    protected SendMailCommand(
        Identifier recipientAddress,
        String subject,
        SendingReason reason,
        MailerService<T> mailerService,
        FileServiceFacade fileServiceFacade,
        MailFormat format,
        Map<String, Object> model
    ) {
        this.mailerService = mailerService;
        this.fileServiceFacade = fileServiceFacade;
        this.recipientAddress = recipientAddress;
        this.subject = subject;
        this.format = format;
        this.model = model;
        this.reason = reason;
    }

    protected Mono<Mail<T>> sendMessage(String messageText) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        return fileServiceFacade
            .getAllByAttachmentUrls((List<String>) model.get("attachmentUrls"))
            .collectList()
            .flatMap(attachments ->
                mailerService
                    .send(
                        recipientAddress,
                        subject,
                        messageText,
                        format,
                        attachments
                    )
            )
            .then(
                mailerService.create(
                    new CreateMailSendingWDTO(
                        subject,
                        recipientAddress,
                        reason,
                        format,
                        model,
                        now
                    )
                )
            )
            .map(id ->
                mailerService.buildEntity(
                    id,
                    recipientAddress,
                    subject,
                    reason,
                    model,
                    format,
                    now,
                    now
                )
            );
    }
}
