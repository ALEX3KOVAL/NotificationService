package ru.alex3koval.notificationService.storage.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class EmailSendingRepositoryImpl<T> implements EmailSendingRepository<T> {
    private final OrmEmailSendingRepository<T> ormRepository;
    private final ObjectMapper objectMapper;
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<MailSendingRDTO<T>> get(T id) {
        return ormRepository.findById(id).map(this::toRdto);
    }

    @Override
    public Mono<T> create(CreateMailSendingWDTO createMailWDTO) {
        try {
            return ormRepository
                .saveWithReturning(ormRepository.toEntity(createMailWDTO, objectMapper))
                .map(EmailSending::getId);
        } catch (JsonProcessingException exc) {
            return Mono.error(exc);
        }
    }

    @Override
    public Mono<T> update(T id, UpdateMailSendingWDTO updateMailSendingWDTO) {
        Map<SqlIdentifier, Object> fieldsForUpdating = new HashMap<>(Map.of());

        if (updateMailSendingWDTO.subject() != null) {
            fieldsForUpdating.put(SqlIdentifier.quoted("subject"), updateMailSendingWDTO.subject());
        }

        if (fieldsForUpdating.isEmpty()) {
            return Mono.just(id);
        }

        fieldsForUpdating.put(
            SqlIdentifier.quoted("updated_at"),
            LocalDateTime.now(ZoneOffset.UTC)
        );

        Query query = Query.query(
            Criteria.where("id").is(id)
        );

        return template
            .update(EmailSending.class)
            .matching(query)
            .apply(Update.from(fieldsForUpdating))
            .thenReturn(id);
    }

    private MailSendingRDTO<T> toRdto(EmailSending<T> entity) {
        return new MailSendingRDTO<>(
            entity.getId(),
            entity.getRecipient(),
            entity.getSubject(),
            entity.getReason(),
            entity.getFormat(),
            entity.getJsonModel()
        );
    }
}
