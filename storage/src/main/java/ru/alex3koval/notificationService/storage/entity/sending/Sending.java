package ru.alex3koval.notificationService.storage.entity.sending;

import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;
import ru.alex3koval.notificationService.domain.vo.SendingRecipient;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;
import ru.alex3koval.notificationService.storage.entity.base.BaseAuditEntity;

@Getter
public abstract class Sending<T> extends BaseAuditEntity<T> {
    @Column("recipient")
    private SendingRecipient recipient;

    @Column("status")
    private SendingStatus status;
}
