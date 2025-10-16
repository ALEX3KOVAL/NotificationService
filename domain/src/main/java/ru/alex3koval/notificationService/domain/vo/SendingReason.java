package ru.alex3koval.notificationService.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum SendingReason {
    OTP((short) 0);

    private final short value;

    public boolean isOTP() {
        return equals(OTP);
    }

    public static Optional<SendingReason> of(short value) {
        return Arrays.stream(SendingReason.values())
            .filter(status -> status.value == value)
            .findFirst();
    }
}
