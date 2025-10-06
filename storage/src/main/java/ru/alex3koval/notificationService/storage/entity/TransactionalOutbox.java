package ru.alex3koval.notificationService.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.alex3koval.eventingContract.vo.EventStatus;
import ru.alex3koval.notificationService.domain.common.vo.Topic;
import ru.alex3koval.notificationService.storage.entity.base.ModifiedAuditEntity;

@Table("transactional_outbox")
@Getter
@AllArgsConstructor
public class TransactionalOutbox<T> extends ModifiedAuditEntity<T> {
    @Column("name")
    private String name;

    @Column("topic")
    private Topic topic;

    @Column("status")
    private EventStatus status;

    @Column("json")
    private String json;
}
