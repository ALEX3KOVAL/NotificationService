package ru.alex3koval.notificationService.domain.command;

import lombok.NonNull;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;

public interface Command<T> {
    @NonNull
    T execute() throws DomainException;
}
