package ru.alex3koval.notificationService.appImpl.command.mail;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.util.List;
import java.util.Map;

public class SendSimpleMailCommandImpl<T> extends SendMailCommand<T> {
    public SendSimpleMailCommandImpl(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        Map<String, Object> model,
        SendingReason reason,
        MailerService<T> mailerService
    ) {
        super(
            recipientAddress,
            subject,
            reason,
            attachmentUrls,
            mailerService,
            MailFormat.TEXT,
            model
        );
    }

    @Override
    @NonNull
    @Transactional
    public Mono<T> execute() {
        return sendMessage(model.get("text").toString())
            .subscribeOn(Schedulers.boundedElastic());
    }
}
