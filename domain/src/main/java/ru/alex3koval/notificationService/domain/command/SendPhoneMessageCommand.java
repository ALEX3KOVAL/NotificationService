package ru.alex3koval.notificationService.domain.command;

import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.vo.OtpReason;
import ru.alex3koval.notificationService.domain.vo.Phone;

public interface SendPhoneMessageCommand<T> extends Command<Mono<T>> {
    record DTO(
        Phone phone,
        OtpReason reason,
        String text
    ) {
    }
}
