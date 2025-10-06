package ru.alex3koval.notificationService.appImpl.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.repository.sending.phone.PhoneSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.UpdatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.service.PhoneService;

import java.util.UUID;

@RequiredArgsConstructor
public class PhoneServiceImpl<T> implements PhoneService<T> {
    private final PhoneSendingRepository<T> repository;

    @NonNull
    @Override
    public UUID send(String phone) {
        return UUID.randomUUID();
    }

    @Override
    public Mono<T> createSending(CreatePhoneSendingWDTO createPhoneSendingWDTO) throws DomainException {
        return repository.create(createPhoneSendingWDTO);
    }

    @Override
    public Mono<T> update(T id, UpdatePhoneSendingWDTO updatePhoneSendingWDTO) {
        return null;
    }
}
