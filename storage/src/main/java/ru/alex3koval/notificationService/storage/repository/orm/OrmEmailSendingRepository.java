package ru.alex3koval.notificationService.storage.repository.orm;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.alex3koval.notificationService.storage.entity.sending.EmailSending;

public interface OrmEmailSendingRepository extends ReactiveCrudRepository<EmailSending, Long> {
}
