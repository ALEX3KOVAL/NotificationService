package ru.alex3koval.notificationService.configuration.di.domainModule;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.PhoneSendingRepository;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.service.PhoneService;
import ru.alex3koval.notificationService.impl.service.MailerServiceImpl;
import ru.alex3koval.notificationService.impl.service.PhoneServiceImpl;

@Configuration
@RequiredArgsConstructor
public class DomainServiceConfiguration {
    private final AppEnvironment env;

    @Bean
    MailerService mailerService(
        JavaMailSender mailSender,
        EmailSendingRepository repository
    ) {
        return new MailerServiceImpl(
            mailSender,
            env.mailer().senderAddress(),
            repository
        );
    }

    @Bean
    PhoneService phonService(
        PhoneSendingRepository repository
    ) {
        return new PhoneServiceImpl(repository);
    }
}
