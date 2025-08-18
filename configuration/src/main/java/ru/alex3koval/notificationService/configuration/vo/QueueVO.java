package ru.alex3koval.notificationService.configuration.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum QueueVO {
    MAIL (1),
    PHONE(2);

    final int amountOfConsumers;
}
