package ru.alex3koval.notificationService.server.api.otp;


import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.MaxRetriesExceededException;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.alex3koval.eventingContract.vo.EventStatus;
import ru.alex3koval.eventingImpl.factory.TransactionalOutBoxReactiveEventPusherFactory;
import ru.alex3koval.notificationService.appImpl.command.factory.SendTemplatedMailCommandFactory;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.domain.command.SendTemplatedMailCommand;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.common.vo.Topic;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpMailRequest;

@RestController
@RequestMapping("otp")
@RequiredArgsConstructor
@Slf4j
public class OtpController {
    private final AppEnvironment appEnv;
    private final SendTemplatedMailCommandFactory<?> sendOtpViaTemplatedMailCommandFactory;
    private final RetryRegistry retryRegistry;

    @Lazy
    private final TransactionalOutBoxReactiveEventPusherFactory<?> transactionalOutBoxEventPusherFactory;

    @PostMapping("email")
    Mono<ServerResponse> sendMail(@RequestBody @Validated SendOtpMailRequest body) throws DomainException {


        SendTemplatedMailCommand.DTO dto = new SendTemplatedMailCommand.DTO(
            body.recipientAddress(),
            body.subject(),
            body.attachmentUrls(),
            appEnv.mailer().otpTemplateFolderPath(),
            appEnv.mailer().otpTemplateName(),
            body.code().toString(),
            body.otpReason()
        );

        return sendOtpViaTemplatedMailCommandFactory
            .create(dto)
            .execute()
            .flatMap(tuple ->
                ServerResponse.ok().bodyValue(tuple.getT2())
            )
            .doOnError(exc -> {
                if (exc instanceof MaxRetriesExceededException) {
                    transactionalOutBoxEventPusherFactory
                        .create()
                        .push(
                            Topic.EMAIL_DLT.name(),
                            EventStatus.CREATED,
                            new TemplatedMailSendingHasBeenRequestedEvent(dto)
                        )
                        .subscribeOn(Schedulers.boundedElastic())
                        .subscribe();
                }
            })
            .transformDeferred(
                RetryOperator.of(retryRegistry.retry("otpRetry"))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }
}
