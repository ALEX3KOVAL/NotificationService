package ru.alex3koval.notificationService.appImpl.command.mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import ru.alex3koval.notificationService.appImpl.core.StringConverters;
import ru.alex3koval.notificationService.domain.command.SendTemplatedMailCommand;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;

import java.io.IOException;

public class SendTemplatedMailCommandImpl<T> extends SendTemplatedMailCommand<T> {
    private final FreeMarkerConfigurer configurer;

    public SendTemplatedMailCommandImpl(
        SendTemplatedMailCommand.DTO dto,
        FreeMarkerConfigurer configurer,
        MailerService<T> mailerService
    ) {
        super(dto, mailerService);
        this.configurer = configurer;
    }

    @Override
    @NonNull
    @Transactional
    public Mono<Tuple2<SendingStatus, T>> execute() throws DomainException {
        try {
            assert model != null;

            configurer.setTemplateLoaderPaths("file:" + templateFolderPath);
            Template template = configurer.getConfiguration().getTemplate(templateFileName);
            String messageText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            return sendMessage(messageText).subscribeOn(Schedulers.boundedElastic());
        } catch (IOException e) {
            String stackTraceWithMessage = StringConverters.stackTracesToString(e.getStackTrace());

            throw new DomainException(
                String.format(
                    "Ошибка получения шаблона %s/%s\n%s\n%s",
                    templateFolderPath,
                    templateFileName,
                    e.getMessage(),
                    stackTraceWithMessage
                )
            );
        } catch (TemplateException e) {
            String stackTraceWithMessage = StringConverters.stackTracesToString(e.getStackTrace());

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
    }
}
