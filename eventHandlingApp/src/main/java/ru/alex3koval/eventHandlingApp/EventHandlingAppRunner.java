package ru.alex3koval.eventHandlingApp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import ru.alex3koval.eventHandlingApp.listener.mail.SimpleMailSendingHasBeenRequestedListener;
import ru.alex3koval.eventHandlingApp.listener.mail.TemplatedMailSendingHasBeenRequestedListener;
import ru.alex3koval.eventHandlingApp.listener.phone.PhoneMessageSendingHasBeenRequestedListener;
import ru.alex3koval.kafkaEventer.EventerPreprocessor;
import ru.alex3koval.notificationService.domain.common.event.PhoneMessageSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.event.SimpleMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.vo.Topic;

import java.util.List;

@RequiredArgsConstructor
public class EventHandlingAppRunner implements ApplicationRunner {
    private final EventerPreprocessor eventerPreprocessor;

    @Override
    public void run(ApplicationArguments args) {
        registerMailListeners(List.of(Topic.EMAIL.getValue(), Topic.EMAIL_DLT.getValue()));
        registerPhoneListeners(List.of(Topic.PHONE.getValue(), Topic.PHONE_DLT.getValue()));
    }

    private void registerMailListeners(List<String> topics) {
        eventerPreprocessor.registerListener(
            topics,
            TemplatedMailSendingHasBeenRequestedListener.class,
            TemplatedMailSendingHasBeenRequestedEvent.class
        );

        eventerPreprocessor.registerListener(
            topics,
            SimpleMailSendingHasBeenRequestedListener.class,
            SimpleMailSendingHasBeenRequestedEvent.class
        );
    }

    private void registerPhoneListeners(List<String> topics) {
        eventerPreprocessor.registerListener(
            topics,
            PhoneMessageSendingHasBeenRequestedListener.class,
            PhoneMessageSendingHasBeenRequestedEvent.class
        );
    }
}
