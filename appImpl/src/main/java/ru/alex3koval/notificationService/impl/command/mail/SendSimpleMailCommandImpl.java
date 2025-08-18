package ru.alex3koval.notificationService.impl.command.mail;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventing.contract.EventPusher;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.util.List;
import java.util.Optional;

public class SendSimpleMailCommandImpl extends SendMailCommand {
    private final String text;

    public SendSimpleMailCommandImpl(
        SendingRecipient recipientAddress,
        String subject,
        Short maxOfAttempts,
        Optional<Short> prevSendingAttemptCount,
        List<String> attachmentUrls,
        String text,
        MailerService mailerService,
        EventPusher eventPusher
    ) {
        super(
            eventPusher,
            recipientAddress,
            subject,
            maxOfAttempts,
            prevSendingAttemptCount,
            attachmentUrls,
            mailerService
        );

        this.text = text;
    }

    @Override
    @NonNull
    @Transactional
    public Mono<Long> execute() {
        return sendMessage(text);
    }
}
