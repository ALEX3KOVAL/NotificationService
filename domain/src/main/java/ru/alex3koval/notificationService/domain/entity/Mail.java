package ru.alex3koval.notificationService.domain.entity;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class Mail {
    private final UUID id;
    private final String subject;
    private final String text;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
