package ru.alex3koval.notificationService.appImpl.command.factory;

import ru.alex3koval.notificationService.domain.command.SendPhoneMessageCommand;
import ru.alex3koval.notificationService.appImpl.command.SendPhoneMessageCommandImpl;

public class SendPhoneMessageCommandFactory<T> {
    public SendPhoneMessageCommand<T> create(
        String phone,
        String text
    ) {
        return new SendPhoneMessageCommandImpl<>(
            phone,
            text
        );
    }
}
