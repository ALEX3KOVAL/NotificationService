package ru.alex3koval.notificationService.domain.command;

import reactor.core.publisher.Mono;
import ru.alex3koval.eventing.contract.Event;
import ru.alex3koval.eventing.contract.EventPusher;
import ru.alex3koval.notificationService.domain.common.event.MailHasBeenSentEvent;
import ru.alex3koval.notificationService.domain.common.event.MailSendingHasBeenFailedEvent;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;

import java.util.List;
import java.util.Optional;

public abstract class SendMailCommand implements Command<Mono<Long>> {
    protected final EventService eventService;
    protected final SendingRecipient recipientAddress;
    protected final String subject;
    protected final Short maxOfAttempts;
    protected final Optional<Short> prevSendingAttemptCount;
    protected final List<String> attachmentUrls;
    protected final MailerService mailerService;

    protected SendMailCommand(
        EventPusher eventPusher,
        SendingRecipient recipientAddress,
        String subject,
        Short maxOfAttempts,
        Optional<Short> prevSendingAttemptCount,
        List<String> attachmentUrls,
        MailerService mailerService
    ) {
        this.eventPusher = eventPusher;
        this.mailerService = mailerService;
        this.recipientAddress = recipientAddress;
        this.subject = subject;
        this.maxOfAttempts = maxOfAttempts;
        this.prevSendingAttemptCount = prevSendingAttemptCount;
        this.attachmentUrls = attachmentUrls;
    }

    protected Mono<Long> sendMessage(String messageText) {
        return mailerService
            .create(
                new CreateMailSendingWDTO(
                    subject,
                    messageText,
                    SendingStatus.PENDING,
                    attachmentUrls
                )
            )
            .zipWhen(sendingId -> {
                try {
                    mailerService.send(
                        recipientAddress.getValue(),
                        subject,
                        messageText
                    );

                    return Mono.just(SendingStatus.SENT);
                }
                catch (Exception e) {
                    return Mono.just(SendingStatus.FAILED);
                }
            })
            .flatMap(tuple ->
                mailerService
                    .update(
                        tuple.getT1(),
                        new UpdateMailSendingWDTO(tuple.getT2())
                    )
                    .then(Mono.just(tuple))
            )
            .flatMap(tuple -> {
                Optional<Event> event;
                Long id = tuple.getT1();
                SendingStatus status = tuple.getT2();

                event = switch (status) {
                    case SENT -> Optional.of(
                        new MailHasBeenSentEvent(
                            subject,
                            messageText,
                            attachmentUrls
                        )
                    );
                    case FAILED -> Optional.of(
                        new MailSendingHasBeenFailedEvent(
                            id,
                            (short) (prevSendingAttemptCount.orElse((short) 0) + 1),
                            maxOfAttempts,
                            subject,
                            messageText
                        )
                    );
                    default -> Optional.empty();
                };

                Mono<? extends Event> eventMono = event
                    .<Mono<? extends Event>>map(Mono::just)
                    .orElseGet(() -> Mono.error(
                        new DomainException(
                            String.format("Статус не может быть: %s при отправке нового/retry письма", tuple.getT2().name())
                        )
                    ));

                return Mono
                    .just(tuple.getT1())
                    .zipWith(eventMono);
            })
            .flatMap(tuple -> {
                try {
                    eventPusher.push(tuple.getT2()); // TODO Curcuit Breaker

                    return Mono.just(tuple.getT1());
                } catch (InterruptedException e) {
                    return Mono.error(
                        new DomainException(
                            String.format(
                                "Не удалось отправить событие %s\n",
                                tuple.getT2()
                            )
                        )
                    );
                }
            });
    }
}
