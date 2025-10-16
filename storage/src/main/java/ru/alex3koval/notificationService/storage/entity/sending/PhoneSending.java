package ru.alex3koval.notificationService.storage.entity.sending;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;

import java.time.LocalDateTime;

@Table(name = "phone_sending")
@Getter
@NoArgsConstructor
public class PhoneSending<T> {
    public PhoneSending(
        SendingRecipient recipient,
        SendingReason reason,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.recipient = recipient;
        this.text = text;
        this.reason = reason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    private T id;
    @Column("recipient")
    private SendingRecipient recipient;
    @Column("reason")
    private SendingReason reason;
    @Column("text")
    private String text;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
