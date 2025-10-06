package ru.alex3koval.notificationService.appImpl.command.factory;

import lombok.RequiredArgsConstructor;
import ru.alex3koval.eventingContract.SyncEventPusher;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.appImpl.command.mail.SendSimpleMailCommandImpl;

import java.util.List;

@RequiredArgsConstructor
public class SendSimpleMailCommandFactory<T> {
    private final MailerService<T> mailerService;
    private final SyncEventPusher eventPusher;
    private final Short maxOfAttempts;

    public SendMailCommand<T> create(
        SendingRecipient recipientAddress,
        String subject,
        String text,
        List<String> attachmentUrls
    ) {
        return new SendSimpleMailCommandImpl<>(
            recipientAddress,
            subject,
            attachmentUrls,
            text,
            mailerService
        );
    }
}
