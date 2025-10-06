package ru.alex3koval.eventHandlingApp.listener.phone;

import org.springframework.stereotype.Component;
import ru.alex3koval.eventingContract.EventListener;
import ru.alex3koval.notificationService.domain.common.event.PhoneMessageSendingHasBeenRequestedEvent;

@Component
public class PhoneMessageSendingHasBeenRequestedListener implements EventListener<PhoneMessageSendingHasBeenRequestedEvent, Void> {
    @Override
    public Void onEvent(PhoneMessageSendingHasBeenRequestedEvent event) {
        return null;
    }
}
