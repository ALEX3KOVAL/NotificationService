package ru.alex3koval.eventHandlingApp.listener.mail;

import ru.alex3koval.eventingContract.EventListener;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;

public class TemplatedMailSendingHasBeenRequestedListener implements EventListener<TemplatedMailSendingHasBeenRequestedEvent, Long> {
    @Override
    public Long onEvent(TemplatedMailSendingHasBeenRequestedEvent event) {
        return null;
    }
}
