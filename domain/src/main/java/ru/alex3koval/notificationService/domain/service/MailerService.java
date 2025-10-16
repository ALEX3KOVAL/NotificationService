package ru.alex3koval.notificationService.domain.service;

import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.base.SendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.MailSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.vo.MailFormat;

public abstract class MailerService<T> extends SendingService<T, CreateMailSendingWDTO, UpdateMailSendingWDTO, MailSendingRDTO<T>> {
    public MailerService(
        SendingRepository<T, CreateMailSendingWDTO, UpdateMailSendingWDTO, MailSendingRDTO<T>> repository
    ) {
        super(repository);
    }

    abstract public Mono<Void> send(
        String recipientAddress,
        String subject,
        String text,
        MailFormat format
    );
}
