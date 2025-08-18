package ru.alex3koval.notificationService.storage.repository.impl;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.phone.PhoneSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.PhoneSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.UpdatePhoneSendingWDTO;
import ru.alex3koval.notificationService.storage.entity.sending.PhoneSending;
import ru.alex3koval.notificationService.storage.repository.jpa.JpaPhoneSendingRepository;

@RequiredArgsConstructor
public class PhoneSendingRepositoryImpl implements PhoneSendingRepository {
    private final JpaPhoneSendingRepository jpaRepository;

    @Override
    public Mono<PhoneSendingRDTO> get(Long id) {
        return jpaRepository
            .findById(id)
            .map(this::toRdto);
    }

    @Override
    public Mono<Long> create(CreatePhoneSendingWDTO createPhoneSendingWDTO) {
        return jpaRepository
            .save(toEntity(createPhoneSendingWDTO))
            .map(PhoneSending::getId);
    }

    @Override
    public Mono<Long> update(Long aLong, UpdatePhoneSendingWDTO updatePhoneSendingWDTO) {
        return null;
    }

    private PhoneSending toEntity(CreatePhoneSendingWDTO dto) {
        return new PhoneSending(dto.code());
    }

    private PhoneSendingRDTO toRdto(PhoneSending sending) {
        return new PhoneSendingRDTO(
            sending.getId(),
            sending.getCode()
        );
    }

}
