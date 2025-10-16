package ru.alex3koval.notificationService.configuration.di.domainModule;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import ru.alex3koval.notificationService.appImpl.command.factory.SendPhoneMessageCommandFactory;
import ru.alex3koval.notificationService.appImpl.command.factory.SendSimpleMailCommandFactory;
import ru.alex3koval.notificationService.appImpl.command.factory.SendTemplatedMailCommandFactory;
import ru.alex3koval.notificationService.appImpl.model.SmsMessageBodyProperties;
import ru.alex3koval.notificationService.domain.service.MailerService;

@Configuration
@RequiredArgsConstructor
public class DomainCommandConfiguration {
    private final MailerService mailerService;

    @Bean
    SendSimpleMailCommandFactory sendOtpViaMailCommandFactory() {
        return new SendSimpleMailCommandFactory(mailerService);
    }

    @Bean
    SendTemplatedMailCommandFactory<?> sendTemplatedMailCommandFactory(
        FreeMarkerConfig freemarkerClassLoaderConfig
    ) {
        return new SendTemplatedMailCommandFactory<>(
            mailerService,
            freemarkerClassLoaderConfig
        );
    }

    @Bean
    SendPhoneMessageCommandFactory<?> sendOtpViaPhoneCommandFactory(
        SmsMessageBodyProperties props
    ) {
        return new SendPhoneMessageCommandFactory<>(props);
    }
}
