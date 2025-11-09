package ru.alex3koval.notificationService.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.alex3koval.notificationService.domain.vo.MailFormat;
import ru.alex3koval.notificationService.domain.vo.SendingReason;
import ru.alex3koval.notificationService.domain.vo.Identifier;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
public class MailSending<T> {
    @Getter
    private final T id;
    private final Identifier recipientAddress;
    private final SendingReason reason;
    private final String subject;
    private final MailFormat format;
    private final Map<String, Object> model;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
