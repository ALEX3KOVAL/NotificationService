package ru.alex3koval.notificationService.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum MailFormat {
    TEXT((short)0),
    HTML((short)1);

    private final short value;

    public boolean isHtml() {
        return equals(HTML);
    }

    public static Optional<MailFormat> of(short value) {
        return Arrays.stream(MailFormat.values())
            .filter(status -> status.value == value)
            .findFirst();
    }
}
