package ru.alex3koval.notificationService.domain.vo;

import java.util.Optional;

public class Phone extends Identifier {
    private Phone(String value) {
        super(value);
    }

    public static Optional<Identifier> of(String value) {
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
