package ru.alex3koval.notificationService.configuration.di.domainModule;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import ru.alex3koval.eventing.contract.EventPusher;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.impl.command.factory.SendOtpViaPhoneCommandFactory;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.impl.command.factory.SendSimpleMailCommandFactory;
import ru.alex3koval.notificationService.impl.command.factory.SendTemplatedMailCommandFactory;

@Configuration
@RequiredArgsConstructor
public class DomainCommandConfiguration {
    private final AppEnvironment env;
    private final MailerService mailerService;
    private final EventPusher eventPusher;

    @Bean
    SendSimpleMailCommandFactory sendOtpViaMailCommandFactory() {
        return new SendSimpleMailCommandFactory(
            mailerService,
            eventPusher,
            env.mailer().maxOfAttempts()
        );
    }

    @Bean
    SendTemplatedMailCommandFactory sendTemplatedMailCommandFactory(
        FreeMarkerConfigurer configurer
    ) {
        return new SendTemplatedMailCommandFactory(
            mailerService,
            eventPusher,
            env.mailer().maxOfAttempts(),
            configurer
        );
    }

    @Bean
    SendOtpViaPhoneCommandFactory sendOtpViaPhoneCommandFactory() {
        return new SendOtpViaPhoneCommandFactory();
    }
}
