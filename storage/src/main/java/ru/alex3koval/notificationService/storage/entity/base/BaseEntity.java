package ru.alex3koval.notificationService.storage.entity.base;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public abstract class BaseEntity<T> {
    @Id
    private T id;
}
