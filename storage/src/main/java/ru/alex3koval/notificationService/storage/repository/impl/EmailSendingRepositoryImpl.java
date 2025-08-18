package ru.alex3koval.notificationService.storage.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.MailSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;
import ru.alex3koval.notificationService.storage.entity.sending.EmailSending;
import ru.alex3koval.notificationService.storage.repository.jpa.JpaEmailSendingRepository;

@RequiredArgsConstructor
public class EmailSendingRepositoryImpl implements EmailSendingRepository {
    private final JpaEmailSendingRepository jpaRepository;
    private final EntityManager entityManager;

    @Override
    public Mono<MailSendingRDTO> get(Long id) {
        return null;
    }

    @Override
    public Mono<Long> create(CreateMailSendingWDTO createMailWDTO) {
        return jpaRepository
            .save(toEntity(createMailWDTO))
            .map(EmailSending::getId);
    }

    @Override
    public Mono<Long> update(Long id, UpdateMailSendingWDTO updateMailSendingWDTO) {
        String fieldsForUpdating = "";

        if (updateMailSendingWDTO.status() != null) {
            fieldsForUpdating += "es.status = :status";
        }

        if (fieldsForUpdating.isEmpty()) {
            return Mono.just(id);
        }

        String finalFieldsForUpdating = fieldsForUpdating;

        return Mono
            .fromCallable(() -> {
                entityManager
                    .createQuery(
                        String.format("UPDATE EmailSending es SET %s WHERE es.id = :id", finalFieldsForUpdating)
                    )
                    .setParameter("id", id)
                    .setParameter("status", updateMailSendingWDTO.status())
                    .executeUpdate();

                return null;
            })
            .thenReturn(id);
    }

    private EmailSending toEntity(CreateMailSendingWDTO dto) {
        return new EmailSending(
            dto.subject(),
            dto.text()
        );
    }
}
