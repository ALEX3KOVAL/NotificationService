package ru.alex3koval.notificationService.appImpl.factory;

import io.github.resilience4j.retry.Retry;
import ru.alex3koval.notificationService.appImpl.service.RetryService;

import java.util.function.Supplier;

public class RetryServiceFactory {
    public RetryService create(
        Retry retry,
        Supplier<Void> onAllRetriesFailed
    ) {
        return new RetryService(
            retry,
            onAllRetriesFailed
        );
    }
}
