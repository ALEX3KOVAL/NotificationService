package ru.alex3koval.notificationService.domain.service;

import lombok.NonNull;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;

import java.util.UUID;

public interface PhoneService extends SendingService<Long, CreatePhoneSendingWDTO> {
    @NonNull
    UUID send(String phone);
}
