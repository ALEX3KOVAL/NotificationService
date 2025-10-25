package ru.alex3koval.notificationService.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public abstract class Identifier {
    private final String value;

    public static Optional<Identifier> from(String value) {
        Optional<Identifier> phoneOptional = Phone.of(value);

        if (phoneOptional.isPresent()) {
            return phoneOptional;
        }

        return Email.of(value);
    }
}
