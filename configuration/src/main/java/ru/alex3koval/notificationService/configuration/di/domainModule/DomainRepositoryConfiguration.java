package ru.alex3koval.notificationService.configuration.di.domainModule;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.PhoneSendingRepository;
import ru.alex3koval.notificationService.storage.repository.impl.EmailSendingRepositoryImpl;
import ru.alex3koval.notificationService.storage.repository.impl.PhoneSendingRepositoryImpl;
import ru.alex3koval.notificationService.storage.repository.jpa.JpaEmailSendingRepository;
import ru.alex3koval.notificationService.storage.repository.jpa.JpaPhoneSendingRepository;

@Configuration
@EnableJpaRepositories("ru.alex3koval.notificationService.storage.repository.jpa")
@EnableJpaAuditing
@EntityScan("ru.alex3koval.notificationService.storage.entity")
public class DomainRepositoryConfiguration {
    @Bean
    EmailSendingRepository emailSendingRepository(JpaEmailSendingRepository jpaRepository) {
        return new EmailSendingRepositoryImpl(jpaRepository);
    }

    @Bean
    PhoneSendingRepository phoneRepository(JpaPhoneSendingRepository jpaRepository) {
        return new PhoneSendingRepositoryImpl(jpaRepository);
    }
}
