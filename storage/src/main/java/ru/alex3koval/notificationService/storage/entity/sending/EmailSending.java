package ru.alex3koval.notificationService.storage.entity.sending;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.Identifier;

import java.time.LocalDateTime;

@Table("email_sending")
@Getter
@Setter
@NoArgsConstructor
public class EmailSending<T> {
    public EmailSending(
        String subject,
        Identifier recipient,
        SendingReason reason,
        MailFormat format,
        String model,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.subject = subject;
        this.recipient = recipient;
        this.jsonModel = model;
        this.format = format;
        this.reason = reason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    private T id;
    @Column("subject")
    private String subject;
    @Column("recipient")
    private Identifier recipient;
    @Column("model")
    private String jsonModel;
    @Column("reason")
    private SendingReason reason;
    @Column("format")
    private MailFormat format;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
