package ru.alex3koval.notificationService.storage.repository.jpa;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.alex3koval.notificationService.storage.entity.Event;

public interface JpaEventRepository extends ReactiveCrudRepository<Event, Long> {
}
