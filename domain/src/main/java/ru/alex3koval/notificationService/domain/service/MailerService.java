package ru.alex3koval.notificationService.domain.service;

import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;

public interface MailerService extends SendingService<Long, CreateMailSendingWDTO, UpdateMailSendingWDTO> {
    void send(
        String recipientAddress,
        String subject,
        String text
    );
}
