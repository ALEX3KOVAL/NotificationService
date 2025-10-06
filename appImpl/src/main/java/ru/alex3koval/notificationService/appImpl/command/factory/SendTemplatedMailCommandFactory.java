package ru.alex3koval.notificationService.appImpl.command.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import ru.alex3koval.notificationService.domain.command.SendTemplatedMailCommand;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.appImpl.command.mail.SendTemplatedMailCommandImpl;

import java.util.Map;

@RequiredArgsConstructor
public class SendTemplatedMailCommandFactory<T> {
    private final MailerService<T> mailerService;
    private final FreeMarkerConfigurer configurer;

    public SendTemplatedMailCommand<T> create(SendTemplatedMailCommand.DTO dto) {
        Map<String, Object> model = Map.of(
            "code", dto.getCode(),
            "reason", dto.getOtpReason()
        );

        dto.setModel(model);

        return new SendTemplatedMailCommandImpl<>(
            dto,
            configurer,
            mailerService
        );
    }
}
