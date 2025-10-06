package ru.alex3koval.notificationService.storage.entity.base;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
public class ModifiedAuditEntity<T> extends BaseAuditEntity<T> {
    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
