package ru.alex3koval.notificationService.storage.entity.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
public class BaseAuditEntity<T> extends BaseEntity<T> {
    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
}
