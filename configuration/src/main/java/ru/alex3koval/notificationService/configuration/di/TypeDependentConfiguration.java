package ru.alex3koval.notificationService.configuration.di;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventingContract.dto.CreateEventWDTO;
import ru.alex3koval.notificationService.appImpl.service.MailerServiceImpl;
import ru.alex3koval.notificationService.appImpl.service.PhoneServiceImpl;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.domain.common.repository.EventRepository;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.PhoneSendingRepository;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.service.PhoneService;
import ru.alex3koval.eventingImpl.factory.TransactionalOutBoxReactiveEventPusherFactory;
import ru.alex3koval.notificationService.domain.vo.Identifier;
import ru.alex3koval.notificationService.storage.repository.impl.EmailSendingRepositoryImpl;
import ru.alex3koval.notificationService.storage.repository.impl.PhoneSendingRepositoryImpl;
import ru.alex3koval.notificationService.storage.repository.impl.TransactionalOutboxRepositoryImpl;
import ru.alex3koval.notificationService.storage.repository.orm.OrmEmailSendingRepository;
import ru.alex3koval.notificationService.storage.repository.orm.OrmEventRepository;
import ru.alex3koval.notificationService.storage.repository.orm.OrmPhoneSendingRepository;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class TypeDependentConfiguration {
    private final AppEnvironment env;

    @Bean
    TransactionalOutBoxReactiveEventPusherFactory<Long> transactionalOutboxReactiveEventPusherFactory(
        @Qualifier("reactiveTransactionalOutBoxPushingFunction")
        Function<CreateEventWDTO, Mono<Long>> pushInDbFunction,
        ObjectMapper mapper
    ) {
        return new TransactionalOutBoxReactiveEventPusherFactory<>(
            pushInDbFunction,
            mapper
        );
    }

    @Bean("reactiveTransactionalOutBoxPushingFunction")
    @SneakyThrows
    Function<CreateEventWDTO, Mono<Long>> pushInDbFunction(
        EventRepository<Long> transactionalOutboxRepository
    ) {
        return transactionalOutboxRepository::add;
    }

    @Bean
    EventRepository<Long> transactionalOutboxRepository(
        OrmEventRepository<Long> ormRepository,
        R2dbcEntityTemplate template
    ) {
        return new TransactionalOutboxRepositoryImpl<>(ormRepository, template);
    }

    @Bean
    MailerService<Long> mailerService(
        JavaMailSender mailSender,
        EmailSendingRepository<Long> repository
    ) {
        return new MailerServiceImpl<>(
            repository,
            mailSender,
            Identifier
                .from(env.mailer().senderAddress())
                .orElseThrow(() ->
                    new RuntimeException("Адрес отправителя некорректен: " + env.mailer().senderAddress())
                )
        );
    }

    @Bean
    PhoneService<Long> phoneService(
        PhoneSendingRepository<Long> repository
    ) {
        return new PhoneServiceImpl<>(repository);
    }

    @Bean
    EmailSendingRepository<Long> emailSendingRepository(
        OrmEmailSendingRepository<Long> jpaRepository,
        R2dbcEntityTemplate template,
        ObjectMapper objectMapper
    ) {
        return new EmailSendingRepositoryImpl<>(jpaRepository, objectMapper, template);
    }

    @Bean
    PhoneSendingRepository<Long> phoneRepository(
        OrmPhoneSendingRepository<Long> jpaRepository,
        R2dbcEntityTemplate template
    ) {
        return new PhoneSendingRepositoryImpl<>(jpaRepository, template);
    }
}
