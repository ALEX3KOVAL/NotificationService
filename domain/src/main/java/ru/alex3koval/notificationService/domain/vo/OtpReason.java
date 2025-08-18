package ru.alex3koval.notificationService.domain.vo;

import java.util.Arrays;
import java.util.Optional;

public enum OtpReason {
    REGISTRATION {
        @Override
        public String toString() {
            return "Регистрация";
        }
    },
    RESTORE_EMAIL {
        @Override
        public String toString() {
            return "Смена электронной почты";
        }
    };

    public static Optional<OtpReason> of(String value) {
        return Arrays.stream(OtpReason.values())
            .filter(status -> status.name().equalsIgnoreCase(value))
            .findFirst();
    }
}
