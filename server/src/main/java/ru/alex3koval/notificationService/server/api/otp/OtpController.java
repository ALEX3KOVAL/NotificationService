package ru.alex3koval.notificationService.server.api.otp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.alex3koval.eventingContract.vo.EventStatus;
import ru.alex3koval.notificationService.appImpl.command.factory.CommandFactory;
import ru.alex3koval.notificationService.appImpl.command.factory.SendPhoneMessageCommandFactory;
import ru.alex3koval.notificationService.appImpl.command.factory.SendTemplatedMailCommandFactory;
import ru.alex3koval.notificationService.appImpl.service.RetryService;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.domain.command.Command;
import ru.alex3koval.notificationService.domain.command.SendPhoneMessageCommand;
import ru.alex3koval.notificationService.domain.command.SendTemplatedMailCommand;
import ru.alex3koval.notificationService.domain.common.event.PhoneMessageSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.common.vo.Topic;
import ru.alex3koval.eventingImpl.factory.TransactionalOutBoxReactiveEventPusherFactory;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpMailRequest;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpViaPhoneRequest;

import java.util.function.Function;

@RestController
@RequestMapping("otp")
@Slf4j
public class OtpController {
    private final AppEnvironment appEnv;
    private final SendTemplatedMailCommandFactory<?> sendOtpViaTemplatedMailCommandFactory;
    private final SendPhoneMessageCommandFactory<?> sendOtpViaPhoneCommandFactory;
    private final TransactionalOutBoxReactiveEventPusherFactory<?> transactionalOutBoxEventPusherFactory;

    private final RetryService retryService;

    public OtpController(
        AppEnvironment appEnv,
        SendTemplatedMailCommandFactory<?> sendOtpViaTemplatedMailCommandFactory,
        SendPhoneMessageCommandFactory<?> sendOtpViaPhoneCommandFactory,
        @Lazy TransactionalOutBoxReactiveEventPusherFactory<?> transactionalOutBoxEventPusherFactory,
        @Qualifier("otpRetry") RetryService retryService
    ) {
        this.appEnv = appEnv;
        this.sendOtpViaTemplatedMailCommandFactory = sendOtpViaTemplatedMailCommandFactory;
        this.transactionalOutBoxEventPusherFactory = transactionalOutBoxEventPusherFactory;
        this.sendOtpViaPhoneCommandFactory = sendOtpViaPhoneCommandFactory;
        this.retryService = retryService;
    }

    @PostMapping("phone")
    Mono<ResponseEntity<?>> sendSms(@RequestBody @Validated SendOtpViaPhoneRequest body) throws DomainException {
        SendPhoneMessageCommand.DTO dto = new SendPhoneMessageCommand.DTO(
            body.phone(),
            body.reason(),
            body.text()
        );

        return sendOtp(
            sendOtpViaPhoneCommandFactory,
            dto,
            new PhoneMessageSendingHasBeenRequestedEvent(
                dto.phone(),
                dto.reason(),
                dto.text()
            ),
            Topic.PHONE_DLT,
            uuid -> Mono.just(ResponseEntity.ok().body(uuid))
        );
    }

    @PostMapping("email")
    Mono<ResponseEntity<?>> sendMail(@RequestBody @Validated SendOtpMailRequest body) throws DomainException {
        SendTemplatedMailCommand.DTO dto = SendTemplatedMailCommand.DTO.ofOTP(
            body.recipientAddress(),
            body.subject(),
            body.attachmentUrls(),
            appEnv.mailer().otpTemplateFolderPath(),
            appEnv.mailer().otpTemplateName(),
            body.code(),
            body.otpReason()
        );

        return sendOtp(
            sendOtpViaTemplatedMailCommandFactory,
            dto,
            TemplatedMailSendingHasBeenRequestedEvent.ofOTP(
                dto.getRecipientAddress(),
                dto.getSubject(),
                dto.getAttachmentUrls(),
                dto.getTemplateFolderPath(),
                dto.getTemplateFileName(),
                body.code(),
                body.otpReason(),
                body.mailFormat(),
                dto.getModel()
            ),
            Topic.EMAIL_DLT,
            id -> Mono.just(ResponseEntity.ok().body(id))
        );
    }

    private <R, T extends Mono<R>, COMMAND extends Command<T>, COMMAND_DTO> Mono<ResponseEntity<?>> sendOtp(
        CommandFactory<COMMAND, COMMAND_DTO> commandFactory,
        COMMAND_DTO dto,
        Object dltEvent,
        Topic dlt,
        Function<R, Mono<ResponseEntity<?>>> mapper
    ) throws DomainException {
        Mono<ResponseEntity<?>> mono = commandFactory
            .create(dto)
            .execute()
            .flatMap(mapper)
            .subscribeOn(Schedulers.boundedElastic());

        return retryService
            .withRetry(
                mono,
                () -> {
                    transactionalOutBoxEventPusherFactory
                        .create()
                        .push(

                            dlt.getValue(),
                            EventStatus.CREATED,
                            dltEvent
                        )
                        .subscribeOn(Schedulers.boundedElastic())
                        .subscribe();
                }
            )
            .subscribeOn(Schedulers.boundedElastic());
    }
}
