package ru.alex3koval.notificationService.domain.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class SimpleMailSendingHasBeenRequestedEvent {
    private final String s;
    private final String k;
}
