package ru.alex3koval.notificationService.impl.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.phone.PhoneSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.service.PhoneService;

import java.util.UUID;

@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {
    private final PhoneSendingRepository repository;

    @NonNull
    @Override
    public UUID send(String phone) {
        return UUID.randomUUID();
    }

    @Override
    public Mono<Long> create(CreatePhoneSendingWDTO createPhoneSendingWDTO) {
        return repository.create(createPhoneSendingWDTO);
    }
}
