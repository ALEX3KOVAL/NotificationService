package ru.alex3koval.notificationService.storage.repository.orm;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.alex3koval.notificationService.storage.entity.sending.PhoneSending;

public interface OrmPhoneSendingRepository<T> extends R2dbcRepository<PhoneSending<T>, T> {
}
