package ru.alex3koval.notificationService.impl.command.factory;

import lombok.RequiredArgsConstructor;
import ru.alex3koval.eventing.contract.EventPusher;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.impl.command.mail.SendSimpleMailCommandImpl;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SendSimpleMailCommandFactory {
    private final MailerService mailerService;
    private final EventPusher eventPusher;
    private final Short maxOfAttempts;

    public SendMailCommand create(
        SendingRecipient recipientAddress,
        String subject,
        String text,
        List<String> attachmentUrls,
        Optional<Short> prevSendingAttemptCount
    ) {
        return new SendSimpleMailCommandImpl(
            recipientAddress,
            subject,
            maxOfAttempts,
            prevSendingAttemptCount,
            attachmentUrls,
            text,
            mailerService,
            eventPusher
        );
    }
}
