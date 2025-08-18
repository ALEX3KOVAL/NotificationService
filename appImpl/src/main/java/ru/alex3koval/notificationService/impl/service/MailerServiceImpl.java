package ru.alex3koval.notificationService.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.CreateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.repository.sending.mail.dto.UpdateMailSendingWDTO;
import ru.alex3koval.notificationService.domain.service.MailerService;

@RequiredArgsConstructor
public class MailerServiceImpl implements MailerService {
    private final JavaMailSender mailSender;
    private final String senderAddress;
    private final EmailSendingRepository repository;

    @Override
    public void send(
        String recipientAddress,
        String subject,
        String text
    ) {
        SimpleMailMessage message = createMessage(
            recipientAddress,
            subject,
            text
        );

        mailSender.send(message);
    }

    @Override
    public Mono<Long> create(CreateMailSendingWDTO dto) {
        return repository.create(dto);
    }

    @Override
    public Mono<Long> update(Long id, UpdateMailSendingWDTO dto) {
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
