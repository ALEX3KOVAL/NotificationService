package ru.alex3koval.notificationService.storage.entity.sending;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_sending")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailSending extends Sending<Long> {
    @Column(
        name = "subject",
        nullable = false,
        updatable = false,
        columnDefinition = "VARCHAR"
    )
    private String subject;

    @Column(
        name = "text",
        nullable = false,
        updatable = false,
        columnDefinition = "VARCHAR"
    )
    private String text;
}
