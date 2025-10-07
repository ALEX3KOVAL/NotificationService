package ru.alex3koval.notificationService.domain.service;

import lombok.NonNull;
import ru.alex3koval.notificationService.domain.repository.base.SendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.PhoneSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.UpdatePhoneSendingWDTO;

import java.util.UUID;

public abstract class PhoneService<T> extends SendingService<T, CreatePhoneSendingWDTO, UpdatePhoneSendingWDTO, PhoneSendingRDTO<T>> {
    public PhoneService(
        SendingRepository<T, CreatePhoneSendingWDTO, UpdatePhoneSendingWDTO, PhoneSendingRDTO<T>> repository
    ) {
        super(repository);
    }

    @NonNull
    abstract public UUID send(String phone);
}
