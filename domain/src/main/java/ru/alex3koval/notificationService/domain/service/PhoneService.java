package ru.alex3koval.notificationService.domain.service;

import lombok.NonNull;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.UpdatePhoneSendingWDTO;

import java.util.UUID;

public interface PhoneService<T> extends SendingService<T, CreatePhoneSendingWDTO, UpdatePhoneSendingWDTO> {
    @NonNull
    UUID send(String phone);
}
