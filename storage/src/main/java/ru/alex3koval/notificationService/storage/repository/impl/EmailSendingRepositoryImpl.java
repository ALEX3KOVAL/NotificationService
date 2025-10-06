package ru.alex3koval.notificationService.storage.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.MailSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;
import ru.alex3koval.notificationService.storage.entity.sending.EmailSending;
import ru.alex3koval.notificationService.storage.repository.orm.OrmEmailSendingRepository;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class EmailSendingRepositoryImpl<T> implements EmailSendingRepository<T> {
    private final OrmEmailSendingRepository jpaRepository;
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<MailSendingRDTO> get(T id) {
        return null;
    }

    @Override
    public Mono<T> create(CreateMailSendingWDTO createMailWDTO) {
        return jpaRepository
            .save(toEntity(createMailWDTO))
            .map(EmailSending<T>::getId);
    }

    @Override
    public Mono<T> update(T id, UpdateMailSendingWDTO updateMailSendingWDTO) {
        Map<SqlIdentifier, Object> fieldsForUpdating = new HashMap<>(Map.of());

        if (updateMailSendingWDTO.status() != null) {
            fieldsForUpdating.put(SqlIdentifier.quoted("status"), updateMailSendingWDTO.status());
        }

        if (fieldsForUpdating.isEmpty()) {
            return Mono.just(id);
        }

        Query query = Query.query(
            Criteria.where("id").is(id)
        );

        return template
            .update(EmailSending.class)
            .matching(query)
            .apply(Update.from(fieldsForUpdating))
            .thenReturn(id);
    }

    private EmailSending<T> toEntity(CreateMailSendingWDTO dto) {
        return new EmailSending<>(
            dto.subject(),
            dto.text()
        );
    }
}
