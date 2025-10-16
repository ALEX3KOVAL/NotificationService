package ru.alex3koval.notificationService.storage.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.alex3koval.eventingContract.vo.EventStatus;

import java.time.LocalDateTime;

@Table("transactional_outbox")
@Getter
@Setter
@NoArgsConstructor
public class TransactionalOutbox<T> {
    public TransactionalOutbox(
        String name,
        String topic,
        EventStatus status,
        String json,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.name = name;
        this.topic = topic;
        this.status = status;
        this.json = json;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    private T id;
    @Column("name")
    private String name;
    @Column("topic")
    private String topic;
    @Column("status")
    private EventStatus status;
    @Column("json")
    private String json;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
