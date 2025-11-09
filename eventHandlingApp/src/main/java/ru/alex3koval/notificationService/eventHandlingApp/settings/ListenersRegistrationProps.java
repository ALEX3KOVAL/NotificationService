package ru.alex3koval.notificationService.eventHandlingApp.settings;

import lombok.Getter;
import ru.alex3koval.notificationService.eventHandlingApp.listener.mail.SimpleMailSendingHasBeenRequestedListener;
import ru.alex3koval.notificationService.eventHandlingApp.listener.mail.TemplatedMailSendingHasBeenRequestedListener;
import ru.alex3koval.notificationService.eventHandlingApp.listener.mail.TestEventListener;
import ru.alex3koval.notificationService.eventHandlingApp.listener.phone.PhoneMessageSendingHasBeenRequestedListener;
import ru.alex3koval.notificationService.domain.common.event.PhoneMessageSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.event.SimpleMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.event.TestEvent;
import ru.alex3koval.notificationService.domain.common.vo.Topic;
import ru.alex3koval.kafkaEventer.ListenersRegistrationConfig;

import java.util.ArrayList;
import java.util.List;

public class ListenersRegistrationProps {
    private final List<ListenersRegistrationConfig.Props<?>> mailListenersProps;
    private final List<ListenersRegistrationConfig.Props<?>> phoneListenersProps;

    @Getter
    private final List<ListenersRegistrationConfig.Props<?>> allProps = new ArrayList<>();

    {
        List<String> topics = List.of(Topic.EMAIL.getValue(), Topic.EMAIL_DLT.getValue());

        phoneListenersProps = List.of(
            new ListenersRegistrationConfig.Props<>(
                topics,
                TemplatedMailSendingHasBeenRequestedListener.class,
                TemplatedMailSendingHasBeenRequestedEvent.class
            ),
            new ListenersRegistrationConfig.Props<>(
                topics,
                SimpleMailSendingHasBeenRequestedListener.class,
                SimpleMailSendingHasBeenRequestedEvent.class
            )
        );
    }

    {
        List<String> topics = List.of(Topic.PHONE.getValue(), Topic.PHONE_DLT.getValue());

        mailListenersProps = List.of(
            new ListenersRegistrationConfig.Props<>(
                topics,
                PhoneMessageSendingHasBeenRequestedListener.class,
                PhoneMessageSendingHasBeenRequestedEvent.class
            ),
            new ListenersRegistrationConfig.Props<>(
                topics,
                TestEventListener.class,
                TestEvent.class
            )
        );
    }

    {
        allProps.addAll(mailListenersProps);
        allProps.addAll(phoneListenersProps);
    }
}
