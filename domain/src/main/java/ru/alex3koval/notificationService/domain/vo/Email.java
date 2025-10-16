package ru.alex3koval.notificationService.domain.vo;

import java.util.Optional;
import java.util.regex.Pattern;

public class Email extends SendingRecipient {
    private Email(String value) {
        super(value);
    }

    private static final Pattern pattern = Pattern.compile(
        "^[a-zA-Z0-9.!#$%&'*+=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)+$"
    );

    public static Optional<SendingRecipient> of(String value) {
        if (value.isEmpty() || !pattern.matcher(value).matches()) {
            return Optional.empty();
        }

        return Optional.of(new Email(value));
    }
}
