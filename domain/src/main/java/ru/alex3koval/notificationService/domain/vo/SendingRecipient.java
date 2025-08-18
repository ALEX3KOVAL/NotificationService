package ru.alex3koval.notificationService.domain.vo;

import java.util.Optional;

public abstract class SendingRecipient {
    public abstract String getValue();

    public static Optional<SendingRecipient> from(String value) {
        Optional<SendingRecipient> phoneOptional = Phone.of(value);

        if (phoneOptional.isPresent()) {
            return phoneOptional;
        }

        return Email.of(value);
    }
}
