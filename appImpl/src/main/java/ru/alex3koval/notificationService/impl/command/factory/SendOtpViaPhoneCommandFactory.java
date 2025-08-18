package ru.alex3koval.notificationService.impl.command.factory;

import ru.alex3koval.notificationService.domain.command.SendOtpViaPhoneCommand;
import ru.alex3koval.notificationService.impl.command.SendOtpViaPhoneCommandImpl;

public class SendOtpViaPhoneCommandFactory {
    public SendOtpViaPhoneCommand create(
        String phone,
        String text
    ) {
        return new SendOtpViaPhoneCommandImpl(
            phone,
            text
        );
    }
}
