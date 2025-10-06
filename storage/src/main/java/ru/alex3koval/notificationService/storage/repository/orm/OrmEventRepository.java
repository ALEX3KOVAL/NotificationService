package ru.alex3koval.notificationService.storage.repository.orm;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.alex3koval.notificationService.storage.entity.TransactionalOutbox;

public interface OrmEventRepository<T> extends ReactiveCrudRepository<TransactionalOutbox<T>, T> {
}
