package ru.alex3koval.notificationService.impl.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.alex3koval.notificationService.domain.command.SendOtpViaPhoneCommand;

@RequiredArgsConstructor
public class SendOtpViaPhoneCommandImpl implements SendOtpViaPhoneCommand {
    private final String phone;
    private final String text;

    @NonNull
    @Override
    public Long execute() {
        return 1L;
    }
}
