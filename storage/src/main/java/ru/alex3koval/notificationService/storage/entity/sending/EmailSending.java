package ru.alex3koval.notificationService.storage.entity.sending;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("email_sending")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailSending<T> extends Sending<T> {
    @Column("subject")
    private String subject;

    @Column("text")
    private String text;
}
