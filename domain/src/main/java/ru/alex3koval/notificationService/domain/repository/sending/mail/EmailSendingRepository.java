package ru.alex3koval.notificationService.domain.repository.sending.mail;

import ru.alex3koval.notificationService.domain.repository.base.BaseRepository;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.MailSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;


public interface EmailSendingRepository<T> extends BaseRepository<T, MailSendingRDTO, CreateMailSendingWDTO, UpdateMailSendingWDTO> {
}
