package ru.alex3koval.notificationService.appImpl.factory;

import lombok.RequiredArgsConstructor;
import ru.alex3koval.notificationService.appImpl.model.RetryConfigurations;
import ru.alex3koval.notificationService.appImpl.service.RetryService;

@RequiredArgsConstructor
public class RetryServiceFactory {
    private final RetryConfigurations retryConfigs;

    public RetryService create(String retryName) {
        return new RetryService(retryConfigs.getConfig(retryName));
    }
}
