package ru.alex3koval.notificationService.domain.common.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum Topic {
    PHONE("phone"),
    PHONE_DLT("phone.DLT"),
    EMAIL("email"),
    EMAIL_DLT("email.DLT");

    private final String value;

    public static Optional<Topic> of(String value) {
        return Arrays.stream(Topic.values())
            .filter(topic -> topic.value.equals(value))
            .findFirst();
    }
}
