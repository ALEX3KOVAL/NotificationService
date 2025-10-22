package ru.alex3koval.notificationService.appImpl.command.mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.alex3koval.notificationService.domain.command.SendTemplatedMailCommand;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.service.MailerService;

import java.io.IOException;

@Slf4j
public class SendTemplatedMailCommandImpl<T> extends SendTemplatedMailCommand<T> {
    private final FreeMarkerConfig freemarkerClassLoaderConfig;

    public SendTemplatedMailCommandImpl(
        SendTemplatedMailCommand.DTO dto,
        FreeMarkerConfig freemarkerClassLoaderConfig,
        MailerService<T> mailerService
    ) {
        super(dto, mailerService);
        this.freemarkerClassLoaderConfig = freemarkerClassLoaderConfig;
    }

    @Override
    @NonNull
    @Transactional
    public Mono<T> execute() throws DomainException {
        try {
            Template template = freemarkerClassLoaderConfig.getConfiguration().getTemplate(templateFileName);
            String messageText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            return sendMessage(messageText);
        } catch (IOException exc) {
            throw new DomainException(
                String.format(
                    "Ошибка получения шаблона %s/%s",
                    templateFolderPath,
                    templateFileName
                ),
                exc
            );
        } catch (TemplateException exc) {
            throw new DomainException(
                String.format(
                    "Ошибка обработки шаблона %s/%s",
                    templateFolderPath,
                    templateFileName
                ),
                exc
            );
        }
    }
}
