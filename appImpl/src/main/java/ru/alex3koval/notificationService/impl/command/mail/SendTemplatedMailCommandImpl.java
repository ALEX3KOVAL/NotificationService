package ru.alex3koval.notificationService.impl.command.mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.NonNull;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventing.contract.EventPusher;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SendTemplatedMailCommandImpl extends SendMailCommand {
    private final Map<String, Object> model;
    private final String templateFolderPath;
    private final String templateFileName;
    private final FreeMarkerConfigurer configurer;

    public SendTemplatedMailCommandImpl(
        SendingRecipient recipientAddress,
        String subject,
        Short maxOfAttempts,
        Optional<Short> prevSendingAttemptCount,
        List<String> attachmentUrls,
        String templateFolderPath,
        String templateFileName,
        Map<String, Object> model,
        FreeMarkerConfigurer configurer,
        MailerService mailerService,
        EventPusher eventPusher
    ) {
        super(
            eventPusher,
            recipientAddress,
            subject,
            maxOfAttempts,
            prevSendingAttemptCount,
            attachmentUrls,
            mailerService
        );

        this.templateFolderPath = templateFolderPath;
        this.templateFileName = templateFileName;
        this.configurer = configurer;
        this.model = model;
    }

    @Override
    public @NonNull Mono<Long> execute() throws DomainException {
        String messageText;

        configurer.setTemplateLoaderPaths("file:" + templateFolderPath);

        try {
            configurer.setTemplateLoaderPaths("file:" + templateFolderPath);
            Template template = configurer.getConfiguration().getTemplate(templateFileName);

            messageText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        }
        catch(IOException e) {
            String stackTraceWithMessage = stackTraceToString(e.getStackTrace());

            throw new DomainException(
                String.format(
                    "Ошибка получения шаблона %s/%s\n%s\n%s",
                    templateFolderPath,
                    templateFileName,
                    e.getMessage(),
                    stackTraceWithMessage
                )
            );
        }
        catch (TemplateException e) {
            String stackTraceWithMessage = stackTraceToString(e.getStackTrace());

            throw new DomainException(
                String.format(
                    "Ошибка обработки шаблона %s/%s\n%s\n%s",
                    templateFolderPath,
                    templateFileName,
                    e.getMessage(),
                    stackTraceWithMessage
                )
            );
        }

        return sendMessage(messageText)
            .doOnError(err -> )
    }

    private String stackTraceToString(StackTraceElement[] stackTrace) {
        return String.join(
            "\n",
            Arrays
                .stream(stackTrace)
                .map(StackTraceElement::toString)
                .toList()
        );
    }
}
