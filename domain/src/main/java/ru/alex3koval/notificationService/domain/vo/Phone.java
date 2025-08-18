package ru.alex3koval.notificationService.domain.vo;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Phone extends SendingRecipient {
    private final String value;

    private Phone(String value) {
        this.value = value;
    }

    public static Optional<SendingRecipient> of(String value) {
        String preprocessed = value.replaceAll("\\D+", "");

        if (
            preprocessed.length() != 11 ||
                preprocessed.charAt(0) != '7' ||
                "89".indexOf(preprocessed.charAt(1)) == -1
        ) {
            return Optional.empty();
        }

        return Optional.of(new Phone(preprocessed));
    }
}
