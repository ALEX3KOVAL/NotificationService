package ru.alex3koval.notificationService.storage.repository.orm;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.alex3koval.notificationService.storage.entity.sending.PhoneSending;

public interface OrmPhoneSendingRepository<T> extends ReactiveCrudRepository<PhoneSending<T>, T> {
}
