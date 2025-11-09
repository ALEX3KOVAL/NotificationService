package ru.alex3koval.notificationService.domain.command;

import lombok.Getter;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.entity.MailSending;
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
    protected final DTO dto;
    protected final MailerService<T> mailerService;
    protected final FileServiceFacade fileServiceFacade;

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
        DTO dto,
        MailerService<T> mailerService,
        FileServiceFacade fileServiceFacade
    ) {
        this.mailerService = mailerService;
        this.fileServiceFacade = fileServiceFacade;
        this.dto = dto;
    }

    protected Mono<MailSending<T>> sendMessage(String messageText) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        return fileServiceFacade
            .getAllByAttachmentUrls((List<String>) dto.model.get("attachmentUrls"))
            .collectList()
            .flatMap(attachments ->
                mailerService
                    .send(
                        dto.recipientAddress,
                        dto.subject,
                        messageText,
                        dto.format,
                        attachments
                    )
            )
            .then(
                mailerService.create(
                    new CreateMailSendingWDTO(
                        dto.subject,
                        dto.recipientAddress,
                        dto.reason,
                        dto.format,
                        dto.model,
                        now,
                        now
                    )
                )
            )
            .map(id ->
                mailerService.buildEntity(
                    id,
                    dto.recipientAddress,
                    dto.subject,
                    dto.reason,
                    dto.model,
                    dto.format,
                    now,
                    now
                )
            );
    }
}
