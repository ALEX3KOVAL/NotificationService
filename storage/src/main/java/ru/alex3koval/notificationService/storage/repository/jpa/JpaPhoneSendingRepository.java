package ru.alex3koval.notificationService.storage.repository.jpa;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.alex3koval.notificationService.storage.entity.sending.PhoneSending;

public interface JpaPhoneSendingRepository extends ReactiveCrudRepository<PhoneSending, Long> {
}
