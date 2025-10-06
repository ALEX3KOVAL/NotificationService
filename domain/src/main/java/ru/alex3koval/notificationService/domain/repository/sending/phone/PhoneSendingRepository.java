package ru.alex3koval.notificationService.domain.repository.sending.phone;

import ru.alex3koval.notificationService.domain.repository.base.BaseRepository;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.CreatePhoneSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.PhoneSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.phone.dto.UpdatePhoneSendingWDTO;

public interface PhoneSendingRepository<T> extends BaseRepository<T, PhoneSendingRDTO<T>, CreatePhoneSendingWDTO, UpdatePhoneSendingWDTO> {
}
