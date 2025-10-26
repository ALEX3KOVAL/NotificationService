package ru.alex3koval.notificationService.appImpl.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import reactor.core.publisher.Mono;
import ru.alex3koval.notificationService.domain.repository.sending.mail.EmailSendingRepository;
import ru.alex3koval.notificationService.domain.service.MailerService;
import ru.alex3koval.notificationService.domain.vo.Identifier;
import ru.alex3koval.notificationService.domain.vo.MailFormat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MailerServiceImpl<T> extends MailerService<T> {
    private final JavaMailSender mailSender;
    private final Identifier senderAddress;

    public MailerServiceImpl(
        EmailSendingRepository<T> repository,
        JavaMailSender mailSender,
        Identifier senderAddress
    ) {
        super(repository);

        this.mailSender = mailSender;
        this.senderAddress = senderAddress;
    }

    @Override
    public Mono<Void> send(
        Identifier recipientAddress,
        String subject,
        String text,
        MailFormat format,
        List<Map.Entry<String, ByteArrayInputStream>> attachments
    ) {
        //return Mono.error(new RuntimeException("AJAJJAJAJAJAJJAJAJAJA"));

        return Mono
            .fromCallable(() -> {
                MimeMessage message = createMessage(
                    recipientAddress,
                    subject,
                    text,
                    format,
                    attachments
                );

                mailSender.send(message);
                return null;
            })
            .then();
    }

    private MimeMessage createMessage(
        Identifier recipientAddress,
        String subject,
        String text,
        MailFormat format,
        List<Map.Entry<String, ByteArrayInputStream>> attachments
    ) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(senderAddress.getValue());
        helper.setTo(recipientAddress.getValue());
        helper.setSubject(subject);
        helper.setText(text, format.isHtml());

        attachments
            .forEach(entry -> {
                try {
                    helper.addAttachment(
                        entry.getKey(),
                        new InputStreamSource() {
                            @Override
                            @NonNull
                            public InputStream getInputStream() {
                                return entry.getValue();
                            }
                        }
                    );
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });

        return message;
    }
}
