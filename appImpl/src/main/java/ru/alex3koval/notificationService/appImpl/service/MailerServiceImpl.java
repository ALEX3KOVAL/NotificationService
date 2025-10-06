package ru.alex3koval.notificationService.appImpl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.common.exception.DomainException;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;

@RequiredArgsConstructor
public class MailerServiceImpl<T> implements MailerService<T> {
    private final JavaMailSender mailSender;
    private final String senderAddress;
    private final EmailSendingRepository<T> repository;

    @Override
    public Mono<SendingStatus> send(
        String recipientAddress,
        String subject,
        String text
    ) {
        return Mono
            .fromCallable(() -> {
                SimpleMailMessage message = createMessage(
                    recipientAddress,
                    subject,
                    text
                );

                mailSender.send(message);

                return SendingStatus.SENT;
            })
            .onErrorReturn(SendingStatus.FAILED);
    }

    @Override
    public Mono<T> createSending(CreateMailSendingWDTO dto) throws DomainException {
        return repository.create(dto);
    }

    @Override
    public Mono<T> update(T id, UpdateMailSendingWDTO dto) {
        return repository.update(id, dto);
    }

    private SimpleMailMessage createMessage(
        String recipientAddress,
        String subject,
        String text
    ) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderAddress);
        message.setTo(recipientAddress);
        message.setSubject(subject);
        message.setText(text);

        return message;
    }
}
