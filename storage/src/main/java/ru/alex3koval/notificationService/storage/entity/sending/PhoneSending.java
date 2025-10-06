package ru.alex3koval.notificationService.storage.entity.sending;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "phone_sending")
@Getter
@AllArgsConstructor
public class PhoneSending<T> extends Sending<T> {
    @Column("code")
    Short code;
}
