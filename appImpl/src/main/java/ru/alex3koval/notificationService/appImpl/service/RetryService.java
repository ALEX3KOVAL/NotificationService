package ru.alex3koval.notificationService.appImpl.service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import ru.alex3koval.notificationService.appImpl.model.RetryConfigurations;

import java.time.Duration;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class RetryService {
    private final RetryConfigurations.Props props;

    public <T> Mono<T> withRetry(
        Mono<T> mono,
        Consumer<Throwable> onAllRetriesFailed
    ) {
        return mono
            .retryWhen(
                withOnRetryExhaustedThrow(buildBaseRetrySpec(), onAllRetriesFailed)
            );
    }

    public <T> Mono<T> withRetry(Mono<T> mono) {
        return mono.retryWhen(
            withOnRetryExhaustedThrow(buildBaseRetrySpec())
        );
    }

    private RetryBackoffSpec buildBaseRetrySpec() {
        return Retry
            .backoff(props.maxAttempts(), Duration.ofMillis(props.minDelay()))
            .jitter(props.jitter());
    }

    private RetryBackoffSpec withOnRetryExhaustedThrow(
        RetryBackoffSpec baseRetry,
        Consumer<Throwable> onAllRetriesFailed
    ) {
        return baseRetry.onRetryExhaustedThrow((spec, signal) -> {
            onAllRetriesFailed.accept(signal.failure());
            return signal.failure();
        });
    }

    private RetryBackoffSpec withOnRetryExhaustedThrow(RetryBackoffSpec baseRetry) {
        return baseRetry.onRetryExhaustedThrow((spec, signal) -> signal.failure());
    }
}
