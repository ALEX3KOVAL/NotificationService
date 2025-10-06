package ru.alex3koval.notificationService.storage.repository.impl;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.phone.PhoneSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.PhoneSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.UpdatePhoneSendingWDTO;
import ru.alex3koval.notificationService.storage.entity.sending.PhoneSending;
import ru.alex3koval.notificationService.storage.repository.orm.OrmPhoneSendingRepository;

@RequiredArgsConstructor
public class PhoneSendingRepositoryImpl<T> implements PhoneSendingRepository<T> {
    private final OrmPhoneSendingRepository<T> jpaRepository;

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
            .map(PhoneSending<T>::getId);
    }

    @Override
    public Mono<T> update(T id, UpdatePhoneSendingWDTO updatePhoneSendingWDTO) {
        return null;
    }

    private PhoneSending<T> toEntity(CreatePhoneSendingWDTO dto) {
        return new PhoneSending<>(dto.code());
    }

    private PhoneSendingRDTO<T> toRdto(PhoneSending<T> sending) {
        return new PhoneSendingRDTO<>(
            sending.getId(),
            sending.getCode()
        );
    }

}
