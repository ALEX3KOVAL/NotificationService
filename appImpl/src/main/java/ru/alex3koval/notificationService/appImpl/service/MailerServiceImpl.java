package ru.alex3koval.notificationService.appImpl.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;

public class MailerServiceImpl<T> extends MailerService<T> {
    private final JavaMailSender mailSender;
    private final String senderAddress;

    public MailerServiceImpl(
        EmailSendingRepository<T> repository,
        JavaMailSender mailSender,
        String senderAddress
    ) {
        super(repository);

        this.mailSender = mailSender;
        this.senderAddress = senderAddress;
    }

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
