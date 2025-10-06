package ru.alex3koval.notificationService.domain.service;

import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;

public interface MailerService<T> extends SendingService<T, CreateMailSendingWDTO, UpdateMailSendingWDTO> {
    Mono<SendingStatus> send(
        String recipientAddress,
        String subject,
        String text
    );
}
