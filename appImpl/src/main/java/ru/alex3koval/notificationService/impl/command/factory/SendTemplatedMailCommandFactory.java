package ru.alex3koval.notificationService.impl.command.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import ru.alex3koval.eventing.contract.EventPusher;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.impl.command.mail.SendTemplatedMailCommandImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class SendTemplatedMailCommandFactory {
    private final MailerService mailerService;
    private final EventPusher eventPusher;
    private final Short maxOfAttempts;
    private final FreeMarkerConfigurer configurer;

    public SendMailCommand create(
        SendingRecipient recipientAddress,
        String subject,
        List<String> attachmentUrls,
        Optional<Short> prevSendingAttemptCount,
        String templateFolderPath,
        String templateFileName,
        String text,
        OtpReason reason
    ) {
        Map<String, Object> model = Map.of(
            "code", Short.valueOf(text),
            "reason", reason.toString()
        );

        return new SendTemplatedMailCommandImpl(
            recipientAddress,
            subject,
            maxOfAttempts,
            prevSendingAttemptCount,
            attachmentUrls,
            templateFolderPath,
            templateFileName,
            model,
            configurer,
            mailerService,
            eventPusher
        );
    }
}
