package ru.alex3koval.eventHandlingApp.listener.mail;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventingContract.EventListener;
import ru.alex3koval.notificationService.appImpl.command.factory.SendSimpleMailCommandFactory;
import ru.alex3koval.notificationService.appImpl.command.mail.SendSimpleMailCommandImpl;
import ru.alex3koval.notificationService.appImpl.service.RetryService;
import ru.alex3koval.notificationService.domain.common.event.SimpleMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.vo.MailFormat;

@RequiredArgsConstructor
public class SimpleMailSendingHasBeenRequestedListener implements EventListener<SimpleMailSendingHasBeenRequestedEvent, Mono<?>> {
    private final SendSimpleMailCommandFactory<?> commandFactory;
    private final RetryService retryService;

    @Override
    @SneakyThrows
    public Mono<?> onEvent(SimpleMailSendingHasBeenRequestedEvent event) {
        return retryService.withRetry(
            commandFactory
                .create(
                    new SendSimpleMailCommandImpl.DTO(
                        event.getRecipientAddress(),
                        event.getSubject(),
                        event.getAttachmentUrls(),
                        MailFormat.TEXT,
                        event.getSendingReason(),
                        event.getText()
                    )
                )
                .execute()
        );
    }
}
