package ru.alex3koval.notificationService.eventHandlingApp.listener.phone;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventingContract.EventListener;
import ru.alex3koval.notificationService.domain.common.event.PhoneMessageSendingHasBeenRequestedEvent;

@Component
public class PhoneMessageSendingHasBeenRequestedListener implements EventListener<PhoneMessageSendingHasBeenRequestedEvent, Mono<?>> {
    @Override
    public Mono<?> onEvent(PhoneMessageSendingHasBeenRequestedEvent event) {
        return null;
    }
}
