package ru.alex3koval.notificationService.appImpl.command.mail;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.entity.MailSending;
import ru.alex3koval.notificationService.domain.service.FileServiceFacade;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.Identifier;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

import java.util.List;
import java.util.Map;

public class SendSimpleMailCommandImpl<T> extends SendMailCommand<T> {
    public SendSimpleMailCommandImpl(
        SendMailCommand.DTO dto,
        MailerService<T> mailerService,
        FileServiceFacade fileServiceFacade
    ) {
        super(
            dto,
            mailerService,
            fileServiceFacade
        );
    }

    @Override
    @NonNull
    @Transactional
    public Mono<T> execute() {
        return sendMessage(dto.getModel().get("text").toString()).map(MailSending::getId);
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
