package ru.alex3koval.notificationService.eventHandlingApp.listener.mail;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.alex3koval.eventingContract.EventListener;
import ru.alex3koval.notificationService.appImpl.command.factory.SendTemplatedMailCommandFactory;
import ru.alex3koval.notificationService.appImpl.service.RetryService;
import ru.alex3koval.notificationService.domain.command.SendTemplatedMailCommand;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;

@RequiredArgsConstructor
public class TemplatedMailSendingHasBeenRequestedListener implements EventListener<TemplatedMailSendingHasBeenRequestedEvent, Mono<?>> {
    private final SendTemplatedMailCommandFactory<?> commandFactory;
    private final RetryService retryService;

    @Override
    @SneakyThrows
    public Mono<?> onEvent(TemplatedMailSendingHasBeenRequestedEvent event) {
        return retryService.withRetry(
            commandFactory
                .create(
                    new SendTemplatedMailCommand.DTO(
                        event.getRecipientAddress(),
                        event.getSubject(),
                        event.getOtpTemplateFolderPath(),
                        event.getOtpTemplateName(),
                        event.getMailFormat(),
                        event.getSendingReason(),
                        event.getModel()
                    )
                )
                .execute()
                .subscribeOn(Schedulers.boundedElastic())
        );
    }
}
