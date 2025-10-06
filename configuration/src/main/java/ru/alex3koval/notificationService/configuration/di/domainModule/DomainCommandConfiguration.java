package ru.alex3koval.notificationService.configuration.di.domainModule;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import ru.alex3koval.eventingContract.SyncEventPusher;
import ru.alex3koval.notificationService.appImpl.command.factory.SendPhoneMessageCommandFactory;
import ru.alex3koval.notificationService.appImpl.command.factory.SendSimpleMailCommandFactory;
import ru.alex3koval.notificationService.appImpl.command.factory.SendTemplatedMailCommandFactory;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.domain.service.MailerService;

@Configuration
@RequiredArgsConstructor
public class DomainCommandConfiguration {
    private final AppEnvironment env;
    private final MailerService<?> mailerService;
    private final SyncEventPusher eventPusher;

    @Bean
    SendSimpleMailCommandFactory<?> sendOtpViaMailCommandFactory() {
        return new SendSimpleMailCommandFactory<>(
            mailerService,
            eventPusher,
            env.mailer().maxOfAttempts()
        );
    }

    @Bean
    SendTemplatedMailCommandFactory<?> sendTemplatedMailCommandFactory(
        FreeMarkerConfigurer configurer
    ) {
        return new SendTemplatedMailCommandFactory<>(
            mailerService,
            configurer
        );
    }

    @Bean
    SendPhoneMessageCommandFactory<?> sendOtpViaPhoneCommandFactory() {
        return new SendPhoneMessageCommandFactory<>();
    }
}
