package ru.alex3koval.notificationService.domain.command;

public interface SendPhoneMessageCommand<T> extends Command<T> {
    record DTO(
        String phone,
        String text
    ) {
    }
}
