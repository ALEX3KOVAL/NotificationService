package ru.alex3koval.notificationService.domain.entity;

import lombok.RequiredArgsConstructor;
import ru.alex3koval.notificationService.domain.vo.SendingStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class Mail {
    private final UUID id;
    private final String subject;
    private final String text;
    private final SendingStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
