package ru.alex3koval.notificationService.storage.entity.sending;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phone_sending")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PhoneSending extends Sending<Long> {
    @Column(
        name = "code",
        nullable = false,
        updatable = false,
        columnDefinition = "SMALLINT"
    )
    Short code;
}
