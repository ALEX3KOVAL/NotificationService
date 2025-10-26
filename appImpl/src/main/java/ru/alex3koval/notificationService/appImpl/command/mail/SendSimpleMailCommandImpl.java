package ru.alex3koval.notificationService.appImpl.command.mail;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.entity.Mail;
import ru.alex3koval.notificationService.domain.service.FileServiceFacade;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.Identifier;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

import java.util.List;
import java.util.Map;

public class SendSimpleMailCommandImpl<T> extends SendMailCommand<T> {
    public SendSimpleMailCommandImpl(
        Identifier recipientAddress,
        String subject,
        Map<String, Object> model,
        SendingReason reason,
        MailerService<T> mailerService,
        FileServiceFacade fileServiceFacade
    ) {
        super(
            recipientAddress,
            subject,
            reason,
            mailerService,
            fileServiceFacade,
            MailFormat.TEXT,
            model
        );
    }

    @Override
    @NonNull
    @Transactional
    public Mono<T> execute() {
        return sendMessage(model.get("text").toString()).map(Mail::getId);
    }

    @Getter
    public static class DTO extends SendMailCommand.DTO {
        public DTO(
            Identifier recipientAddress,
            String subject,
            List<String> attachmentUrls,
            MailFormat format,
            SendingReason reason,
            String text
        ) {
            super(
                recipientAddress,
                subject,
                reason,
                format,
                Map.of("text", text, "attachmentUrls", attachmentUrls)
            );
        }
    }
}
