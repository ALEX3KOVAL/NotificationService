package ru.alex3koval.notificationService.storage.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.phone.PhoneSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.PhoneSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.UpdatePhoneSendingWDTO;
import ru.alex3koval.notificationService.storage.entity.sending.EmailSending;
import ru.alex3koval.notificationService.storage.entity.sending.PhoneSending;
import ru.alex3koval.notificationService.storage.repository.orm.OrmPhoneSendingRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class PhoneSendingRepositoryImpl<T> implements PhoneSendingRepository<T> {
    private final OrmPhoneSendingRepository<T> jpaRepository;
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<PhoneSendingRDTO<T>> get(T id) {
        return jpaRepository
            .findById(id)
            .map(this::toRdto);
    }

    @Override
    public Mono<T> create(CreatePhoneSendingWDTO createPhoneSendingWDTO) {
        return jpaRepository
            .save(toEntity(createPhoneSendingWDTO))
            .map(PhoneSending::getId);
    }

    @Override
    public Mono<T> update(T id, UpdatePhoneSendingWDTO updatePhoneSendingWDTO) {
        Map<SqlIdentifier, Object> fieldsForUpdating = new HashMap<>(Map.of());

        if (updatePhoneSendingWDTO.recipient() != null) {
            fieldsForUpdating.put(SqlIdentifier.quoted("recipient"), updatePhoneSendingWDTO.recipient());
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
            .update(PhoneSending.class)
            .matching(query)
            .apply(Update.from(fieldsForUpdating))
            .thenReturn(id);
    }

    private PhoneSending<T> toEntity(CreatePhoneSendingWDTO dto) {
        return new PhoneSending<>(
            dto.recipient(),
            dto.reason(),
            dto.text(),
            dto.createdAt(),
            dto.createdAt() // при создании updated_at такой же, как и created_at
        );
    }

    private PhoneSendingRDTO<T> toRdto(PhoneSending<T> sending) {
        return new PhoneSendingRDTO<>(
            sending.getId(),
            sending.getRecipient(),
            sending.getReason(),
            sending.getText()
        );
    }

}
