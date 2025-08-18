package ru.alex3koval.eventing.contract;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public abstract class Event {
    private final String name;
    private final LocalDateTime createdAt = LocalDateTime.now();
}
