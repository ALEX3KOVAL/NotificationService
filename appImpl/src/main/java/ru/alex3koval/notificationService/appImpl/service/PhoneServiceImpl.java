package ru.alex3koval.notificationService.appImpl.service;

import lombok.NonNull;
import ru.alex3koval.notificationService.domain.repository.base.SendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.PhoneSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.UpdatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.service.PhoneService;

import java.util.UUID;

public class PhoneServiceImpl<T> extends PhoneService<T> {
    public PhoneServiceImpl(
        SendingRepository<T, CreatePhoneSendingWDTO, UpdatePhoneSendingWDTO, PhoneSendingRDTO<T>> repository
    ) {
        super(repository);
    }

    @NonNull
    public UUID send(String phone) {
        return UUID.randomUUID();
    }
}
