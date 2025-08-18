package ru.alex3koval.notificationService.storage.repository.impl;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.event.EventRepository;
import ru.alex3koval.notificationService.domain.repository.event.dto.CreateEventWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.MailSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;
import ru.alex3koval.notificationService.storage.entity.Event;
import ru.alex3koval.notificationService.storage.entity.sending.EmailSending;
import ru.alex3koval.notificationService.storage.repository.jpa.JpaEventRepository;

@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {
    private final JpaEventRepository jpaRepository;

    @Override
    public Mono<MailSendingRDTO> get(Long id) {
        return toEntity(jpaRepository.findById(id));
    }

    @Override
    public Mono<Long> create(CreateMailSendingWDTO createMailSendingWDTO) {
        return null;
    }

    @Override
    public Mono<Long> update(Long aLong, UpdateMailSendingWDTO updateMailSendingWDTO) {
        return null;
    }

    private Event toEntity(CreateEventWDTO dto) {
        return new Event(
            dto.name()
        );
    }
}
