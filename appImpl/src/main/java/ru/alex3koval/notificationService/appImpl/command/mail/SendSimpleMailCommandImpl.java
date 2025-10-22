package ru.alex3koval.notificationService.appImpl.command.mail;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.util.List;
import java.util.Map;

public class SendSimpleMailCommandImpl<T> extends SendMailCommand<T> {
    public SendSimpleMailCommandImpl(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        Map<String, Object> model,
        SendingReason reason,
        MailerService<T> mailerService
    ) {
        super(
            recipientAddress,
            subject,
            reason,
            attachmentUrls,
            mailerService,
            MailFormat.TEXT,
            model
        );
    }

    @Override
    @NonNull
    @Transactional
    public Mono<T> execute() {
        return sendMessage(model.get("text").toString());
    }

    @Getter
    public static class DTO extends SendMailCommand.DTO {
        public DTO(
            SendingRecipient recipientAddress,
            String subject,
            List<String> attachmentUrls,
            MailFormat format,
            SendingReason reason,
            String text
        ) {
            super(recipientAddress, subject, attachmentUrls, reason, format, Map.of("text", text));
        }
    }
}
