package ru.alex3koval.notificationService.domain.common.event;

import lombok.Getter;
import lombok.Setter;
import ru.alex3koval.eventing.contract.Event;

import java.util.List;

@Getter
@Setter
public class MailHasBeenSentEvent extends Event {
    private Long sendingId;
    private final String subject;
    private final String text;
    private List<String> attachmentUrls;

    public MailHasBeenSentEvent(
        String subject,
        String text,
        List<String> attachmentUrls
    ) {
        super(MailHasBeenSentEvent.class.getSimpleName());

        this.subject = subject;
        this.text = text;
        this.attachmentUrls = attachmentUrls;
    }
}
