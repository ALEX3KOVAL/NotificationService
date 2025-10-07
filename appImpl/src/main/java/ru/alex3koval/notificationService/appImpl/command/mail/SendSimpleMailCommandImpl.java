package ru.alex3koval.notificationService.appImpl.command.mail;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;

import java.util.List;

public class SendSimpleMailCommandImpl<T> extends SendMailCommand<T> {
    private final String text;

    public SendSimpleMailCommandImpl(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        String text,
        MailerService<T> mailerService
    ) {
        super(
            recipientAddress,
            subject,
            attachmentUrls,
            mailerService
        );
        this.text = text;
    }

    @Override
    @NonNull
    @Transactional
    public Mono<Tuple2<SendingStatus, T>> execute() {
        return sendMessage(text).subscribeOn(Schedulers.boundedElastic());
    }
}
