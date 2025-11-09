package ru.alex3koval.notificationService.domain.service;

import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.entity.MailSending;
import ru.alex3koval.notificationService.domain.repository.base.SendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.MailSendingRDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.vo.Identifier;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public abstract class MailerService<T> extends SendingService<T, CreateMailSendingWDTO, UpdateMailSendingWDTO, MailSendingRDTO<T>> {
    public MailerService(
        SendingRepository<T, CreateMailSendingWDTO, UpdateMailSendingWDTO, MailSendingRDTO<T>> repository
    ) {
        super(repository);
    }

    public abstract Mono<Void> send(
        Identifier recipientAddress,
        String subject,
        String text,
        MailFormat format,
        List<Map.Entry<String, ByteArrayInputStream>> attachmentsFlux
    );

    public MailSending<T> buildEntity(
        T id,
        Identifier recipientAddress,
        String subject,
        SendingReason reason,
        Map<String, Object> model,
        MailFormat mailFormat,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        return new MailSending<>(
            id,
            recipientAddress,
            reason,
            subject,
            mailFormat,
            model,
            createdAt,
            updatedAt
        );
    }
}
