package ru.alex3koval.notificationService.appImpl.command.factory;

import lombok.RequiredArgsConstructor;
import ru.alex3koval.notificationService.appImpl.command.SendPhoneMessageCommandImpl;
import ru.alex3koval.notificationService.appImpl.model.SmsMessageBodyProperties;
import ru.alex3koval.notificationService.domain.command.SendPhoneMessageCommand;

@RequiredArgsConstructor
public class SendPhoneMessageCommandFactory<T> implements CommandFactory<SendPhoneMessageCommand<T>, SendPhoneMessageCommand.DTO> {
    private final SmsMessageBodyProperties props;

    @Override
    public SendPhoneMessageCommand<T> create(SendPhoneMessageCommand.DTO dto) {
        props.replaceAnchors(dto.text(), dto.reason());

        return new SendPhoneMessageCommandImpl<>(
            dto.phone(),
            props.getSubject(),
            props.getBody()
        );
    }
}
