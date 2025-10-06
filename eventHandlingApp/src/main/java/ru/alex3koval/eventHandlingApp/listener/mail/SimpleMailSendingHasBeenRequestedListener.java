package ru.alex3koval.eventHandlingApp.listener.mail;

import ru.alex3koval.eventingContract.EventListener;
import ru.alex3koval.notificationService.domain.common.event.SimpleMailSendingHasBeenRequestedEvent;

public class SimpleMailSendingHasBeenRequestedListener implements EventListener<SimpleMailSendingHasBeenRequestedEvent, Long> {
    @Override
    public Long onEvent(SimpleMailSendingHasBeenRequestedEvent event) {
        return 0L;
    }
}
