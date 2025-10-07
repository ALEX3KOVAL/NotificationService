package ru.alex3koval.notificationService.domain.repository.sending.phone;

import ru.alex3koval.notificationService.domain.repository.base.SendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.PhoneSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.UpdatePhoneSendingWDTO;

public interface PhoneSendingRepository<T> extends SendingRepository<T, CreatePhoneSendingWDTO, UpdatePhoneSendingWDTO, PhoneSendingRDTO<T>> {
}
