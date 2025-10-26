package ru.alex3koval.notificationService.appImpl.command.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import ru.alex3koval.notificationService.appImpl.command.mail.SendTemplatedMailCommandImpl;
import ru.alex3koval.notificationService.domain.command.SendTemplatedMailCommand;
import ru.alex3koval.notificationService.domain.service.FileServiceFacade;
import ru.alex3koval.notificationService.domain.service.MailerService;


@RequiredArgsConstructor
public class SendTemplatedMailCommandFactory<T> implements CommandFactory<SendTemplatedMailCommand<T>, SendTemplatedMailCommand.DTO> {
    private final MailerService<T> mailerService;
    private final FileServiceFacade fileServiceFacade;
    private final FreeMarkerConfig freemarkerClassLoaderConfig;

    @Override
    public SendTemplatedMailCommand<T> create(SendTemplatedMailCommand.DTO dto) {
        return new SendTemplatedMailCommandImpl<>(
            dto,
            freemarkerClassLoaderConfig,
            mailerService,
            fileServiceFacade
        );
    }
}
