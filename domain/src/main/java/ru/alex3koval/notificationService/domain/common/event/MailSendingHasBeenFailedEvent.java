package ru.alex3koval.notificationService.domain.common.event;

import lombok.Getter;
import lombok.Setter;
import ru.alex3koval.eventing.contract.Event;

@Getter
@Setter
public class MailSendingHasBeenFailedEvent extends Event {
    private final Long sendingId;
    private final String subject;
    private final String text;
    private final Short attemptCount;
    private final Short maxOfAttempts;

    public MailSendingHasBeenFailedEvent(
        Long sendingId,
        Short attemptCount,
        Short maxOfAttempts,
        String subject,
        String text
    ) {
        super(MailSendingHasBeenFailedEvent.class.getSimpleName());

        this.sendingId = sendingId;
        this.attemptCount = attemptCount;
        this.maxOfAttempts = maxOfAttempts;
        this.subject = subject;
        this.text = text;
    }
}
