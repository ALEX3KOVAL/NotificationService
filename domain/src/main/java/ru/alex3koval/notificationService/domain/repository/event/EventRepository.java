package ru.alex3koval.notificationService.domain.repository.event;

import ru.alex3koval.notificationService.domain.repository.base.BaseRepository;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.MailSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;

public interface EventRepository extends BaseRepository<Long, MailSendingRDTO, CreateMailSendingWDTO, UpdateMailSendingWDTO> {
}
