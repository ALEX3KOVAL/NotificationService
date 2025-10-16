package ru.alex3koval.notificationService.appImpl.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.command.SendPhoneMessageCommand;
import ru.alex3koval.notificationService.domain.vo.Phone;


@RequiredArgsConstructor
public class SendPhoneMessageCommandImpl<T> implements SendPhoneMessageCommand<T> {
    private final Phone phone;
    private final String subject;
    private final String text;

    @NonNull
    @Override
    public Mono<T> execute() {
        return null;
    }
}
