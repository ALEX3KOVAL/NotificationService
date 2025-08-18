package ru.alex3koval.notificationService.server.api.otp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import ru.alex3koval.eventing.contract.EventPusher;
import ru.alex3koval.eventing.impl.factory.MessageListenerContainerFactory;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.domain.common.event.MailHasBeenSentEvent;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.impl.command.factory.SendOtpViaPhoneCommandFactory;
import ru.alex3koval.notificationService.impl.command.factory.SendTemplatedMailCommandFactory;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpMailRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("otp")
@RequiredArgsConstructor
public class OtpController {
    private final AppEnvironment env;
    private final SendTemplatedMailCommandFactory sendOtpViaTemplatedMailCommandFactory;
    private final SendOtpViaPhoneCommandFactory sendOtpViaPhoneCommandFactory;
    private final MessageListenerContainerFactory containerFactory;

    /*@RequestMapping(value = "/test",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<String> test() {
        SimpleMessageListenerContainer container = containerFactory.create();

        return Flux.create(sink -> {
            container.setupMessageListener(
                (ChannelAwareMessageListener)(Message message, Channel channel) -> {
                    if (sink.isCancelled()) {
                        container.stop();
                        return;
                    }
                    String msg = new String(message.getBody());
                    sink.next(msg);
                }
            );

            //Start the container and stop the container
            sink.onRequest(r -> container.start());
            sink.onDispose(container::stop);
        });
    }*/

    @PostMapping("event")
    Mono<ResponseEntity<Void>> sendEvent(EventPusher pusher) {
        return Mono
            .fromCallable(() -> {
                pusher
                    .push(
                        new MailHasBeenSentEvent(
                            "Новая тема",
                            "Новый текст",
                            List.of("link1", "link2", "link3")
                        )
                    );

                return null;
            })
            .subscribeOn(Schedulers.boundedElastic())
            .thenReturn(ResponseEntity.ok().build());
    }

    @PostMapping("email")
    Mono<ResponseEntity<Void>> sendMail(@RequestBody @Validated SendOtpMailRequest body) throws DomainException {
        sendOtpViaTemplatedMailCommandFactory
            .create(
                body.recipientAddress(),
                body.subject(),
                body.attachmentUrls(),
                Optional.empty(),
                env.mailer().otpTemplateFolderPath(),
                env.mailer().otpTemplateName(),
                body.code().toString(),
                body.otpReason()
            )
            .execute()
            .doOnError(err ->)
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe(System.out::println);

        return Mono.just(ResponseEntity.accepted().build());
    }

    /*@PostMapping("phone")
    SendMailResponse sendOtpViaPhone(@RequestBody @Validated SendOtpViaPhoneRequest body) {
        UUID sendingId = sendOtpViaPhoneCommandFactory
            .create(
                body.phone(),
                body.text()
            )
            .execute();

        return new SendMailResponse(sendingId);
    }*/
}
