package ru.alex3koval.notificationService.appImpl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import ru.alex3koval.notificationService.appImpl.model.RetryConfigurations;

import java.time.Duration;

@RequiredArgsConstructor
public class RetryService {
    private final RetryConfigurations.Props props;

    public <T> Mono<T> withRetry(
        Mono<T> mono,
        Runnable onAllRetriesFailed
    ) {
        return mono
            .retryWhen(
                withOnRetryExhaustedThrow(buildBaseRetrySpec(), onAllRetriesFailed)
            );
    }

    public <T> Mono<T> withRetry(Mono<T> mono) {
        return mono
            .retryWhen(
                withOnRetryExhaustedThrow(buildBaseRetrySpec())
            );
    }

    private RetryBackoffSpec buildBaseRetrySpec() {
        return Retry
            .backoff(props.maxAttempts(), Duration.ofMillis(props.minDelay()))
            .jitter(props.jitter());
    }

    private RetryBackoffSpec withOnRetryExhaustedThrow(RetryBackoffSpec baseRetry, Runnable onAllRetriesFailed) {
        return baseRetry.onRetryExhaustedThrow((spec, signal) -> {
            onAllRetriesFailed.run();
            return signal.failure();
        });
    }

    private RetryBackoffSpec withOnRetryExhaustedThrow(RetryBackoffSpec baseRetry) {
        return baseRetry.onRetryExhaustedThrow((spec, signal) -> signal.failure());
    }
}
