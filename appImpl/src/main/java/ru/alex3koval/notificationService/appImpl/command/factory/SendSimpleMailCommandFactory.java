package ru.alex3koval.notificationService.appImpl.command.factory;

import lombok.RequiredArgsConstructor;
import ru.alex3koval.notificationService.appImpl.command.mail.SendSimpleMailCommandImpl;
import ru.alex3koval.notificationService.domain.command.SendMailCommand;
import ru.alex3koval.notificationService.domain.service.FileServiceFacade;
import ru.alex3koval.notificationService.domain.service.MailerService;

@RequiredArgsConstructor
public class SendSimpleMailCommandFactory<T> implements CommandFactory<SendMailCommand<T>, SendMailCommand.DTO> {
    private final MailerService<T> mailerService;
    private final FileServiceFacade fileServiceFacade;

    @Override
    public SendMailCommand<T> create(SendMailCommand.DTO dto) {
        return new SendSimpleMailCommandImpl<>(
            dto.getRecipientAddress(),
            dto.getSubject(),
            dto.getModel(),
            dto.getReason(),
            mailerService,
            fileServiceFacade
        );
    }
}
