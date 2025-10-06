package ru.alex3koval.notificationService.domain.command;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;

import java.util.List;

public abstract class SendMailCommand<T> implements Command<Mono<Tuple2<SendingStatus, T>>> {
    //protected final EventService eventService;
    protected final SendingRecipient recipientAddress;
    protected final String subject;
    protected final List<String> attachmentUrls;
    protected final MailerService<T> mailerService;

    protected SendMailCommand(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        MailerService<T> mailerService
    ) {
        this.mailerService = mailerService;
        this.recipientAddress = recipientAddress;
        this.subject = subject;
        this.attachmentUrls = attachmentUrls;
    }

    protected Mono<Tuple2<SendingStatus, T>> sendMessage(String messageText) {
        return mailerService
            .send(
                recipientAddress.getValue(),
                subject,
                messageText
            )
            .zipWhen( sendingStatus -> {
                try {
                    return mailerService.createSending(
                        new CreateMailSendingWDTO(
                            subject,
                            messageText,
                            sendingStatus,
                            attachmentUrls
                        )
                    );
                }
                catch (DomainException exc) {
                    return Mono.error(exc);
                }
            });
    }
}
