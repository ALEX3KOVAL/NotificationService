package ru.alex3koval.notificationService.storage.repository.jpa;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.alex3koval.notificationService.storage.entity.sending.EmailSending;

public interface JpaEmailSendingRepository extends ReactiveCrudRepository<EmailSending, Long> {

}
