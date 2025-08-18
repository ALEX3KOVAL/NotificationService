package ru.alex3koval.notificationService.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.alex3koval.notificationService.storage.entity.base.BaseAuditEntity;

@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Event extends BaseAuditEntity<Long> {
    @Column(
        name = "name",
        nullable = false,
        updatable = false,
        columnDefinition = "VARCHAR"
    )
    private String name;
}
