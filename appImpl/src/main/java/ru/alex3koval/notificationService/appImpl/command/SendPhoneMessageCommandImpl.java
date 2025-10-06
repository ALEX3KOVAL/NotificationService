package ru.alex3koval.notificationService.appImpl.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.alex3koval.notificationService.domain.command.SendPhoneMessageCommand;

@RequiredArgsConstructor
public class SendPhoneMessageCommandImpl<T> implements SendPhoneMessageCommand<T> {
    private final String phone;
    private final String text;

    public SendPhoneMessageCommandImpl(
        SendPhoneMessageCommand.DTO dto
    ) {
        this.phone = dto.phone();
        this.text = dto.text();
    }

    @NonNull
    @Override
    public T execute() {
        return null;
    }
}
