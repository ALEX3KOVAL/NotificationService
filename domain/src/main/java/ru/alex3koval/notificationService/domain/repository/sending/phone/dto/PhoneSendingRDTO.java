package ru.alex3koval.notificationService.domain.repository.sending.phone.dto;

public record PhoneSendingRDTO<T>(
    T id,
    Short code
) {
}
