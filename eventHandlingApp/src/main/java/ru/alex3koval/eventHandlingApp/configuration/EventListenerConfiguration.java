package ru.alex3koval.eventHandlingApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alex3koval.eventHandlingApp.listener.mail.SimpleMailSendingHasBeenRequestedListener;
import ru.alex3koval.eventHandlingApp.listener.mail.TemplatedMailSendingHasBeenRequestedListener;
import ru.alex3koval.eventHandlingApp.listener.mail.TestEventListener;
import ru.alex3koval.eventHandlingApp.listener.phone.PhoneMessageSendingHasBeenRequestedListener;
import ru.alex3koval.notificationService.appImpl.command.factory.SendSimpleMailCommandFactory;
import ru.alex3koval.notificationService.appImpl.command.factory.SendTemplatedMailCommandFactory;
import ru.alex3koval.notificationService.appImpl.service.RetryService;

@Configuration
public class EventListenerConfiguration {
    @Bean
    TestEventListener testEventListener() {
        return new TestEventListener();
    }

    @Bean
    TemplatedMailSendingHasBeenRequestedListener templatedMailSendingHasBeenRequestedListener(
        SendTemplatedMailCommandFactory<Long> commandFactory,
        RetryService retryService
    ) {
        return new TemplatedMailSendingHasBeenRequestedListener(
            commandFactory,
            retryService
        );
    }

    @Bean
    SimpleMailSendingHasBeenRequestedListener simpleMailSendingHasBeenRequestedListener(
        SendSimpleMailCommandFactory<Long> commandFactory,
        RetryService retryService
    ) {
        return new SimpleMailSendingHasBeenRequestedListener(commandFactory, retryService);
    }

    @Bean
    PhoneMessageSendingHasBeenRequestedListener phoneMessageSendingHasBeenRequestedListener() {
        return new PhoneMessageSendingHasBeenRequestedListener();
    }
}
