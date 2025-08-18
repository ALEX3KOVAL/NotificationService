package ru.alex3koval.notificationService.eventHandlingApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alex3koval.notificationService.eventHandlingApp.listener.mail.MailHasBeenSentListener;
import ru.alex3koval.notificationService.eventHandlingApp.listener.mail.MailSendingHasBeenFailedListener;
import ru.alex3koval.notificationService.eventHandlingApp.listener.phone.OtpHasBeenSentViaPhoneListener;

@Configuration
public class EventListenerConfiguration {
    @Bean
    MailSendingHasBeenFailedListener mailSendingHasBeenFailedListener() {
        return new MailSendingHasBeenFailedListener();
    }

    @Bean
    MailHasBeenSentListener mailHasBeenSentListener() {
        return new MailHasBeenSentListener();
    }

    @Bean
    OtpHasBeenSentViaPhoneListener otpHasBeenSentViaPhoneListener() {
        return new OtpHasBeenSentViaPhoneListener();
    }
}
