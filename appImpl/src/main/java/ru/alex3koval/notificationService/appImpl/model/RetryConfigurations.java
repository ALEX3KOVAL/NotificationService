package ru.alex3koval.notificationService.appImpl.model;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class RetryConfigurations {
    private final Map<String, Props> configs;

    public record Props(
        short maxAttempts,
        long minDelay,
        float jitter
    ) {
    }

    public Props getConfig(String name) {
        return configs.get(name);
    }
}
