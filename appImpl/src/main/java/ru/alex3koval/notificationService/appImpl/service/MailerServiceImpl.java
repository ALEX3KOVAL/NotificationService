package ru.alex3koval.notificationService.appImpl.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.MailFormat;

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
    public Mono<Void> send(
        String recipientAddress,
        String subject,
        String text,
        MailFormat format
    ) {
        //return Mono.error(new RuntimeException("AJAJJAJAJAJAJJAJAJAJA"));

        return Mono
            .fromCallable(() -> {
                MimeMessage message = createMessage(
                    recipientAddress,
                    subject,
                    text,
                    format
                );

                mailSender.send(message);
                return null;
            });
    }

    private MimeMessage createMessage(
        String recipientAddress,
        String subject,
        String text,
        MailFormat format
    ) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(senderAddress);
        helper.setTo(recipientAddress);
        helper.setSubject(subject);
        helper.setText(text, format.isHtml());

        return message;
    }
}
