package ru.alex3koval.notificationService.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor @Getter
public enum SendingStatus {
    SENT((short)1),
    PENDING((short)0),
    FAILED((short)2);

    final short value;

    public boolean isSent() {
        return equals(SENT);
    }

    public static Optional<SendingStatus> of(short value) {
        return Arrays.stream(SendingStatus.values())
            .filter(status -> status.value == value)
            .findFirst();
    }
}
