package ru.alex3koval.notificationService.storage.entity.sending;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;
import ru.alex3koval.notificationService.storage.converter.SendingRecipientConverter;
import ru.alex3koval.notificationService.storage.entity.base.BaseAuditEntity;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class Sending<T> extends BaseAuditEntity<T> {
    @Column(
        name = "recipient",
        nullable = false,
        updatable = false,
        columnDefinition = "VARCHAR"
    )
    @Convert(converter = SendingRecipientConverter.class)
    private SendingRecipient recipient;

    @Column(
        name = "status",
        nullable = false,
        columnDefinition = "SMALLINT"
    )
    private SendingStatus status;
}
