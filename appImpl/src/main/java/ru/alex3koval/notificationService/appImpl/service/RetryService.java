package ru.alex3koval.notificationService.appImpl.service;

import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.event.RetryOnErrorEvent;
import io.github.resilience4j.retry.event.RetryOnRetryEvent;
import io.github.resilience4j.retry.event.RetryOnSuccessEvent;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public class RetryService {
    private final Retry retry;

    public RetryService(Retry retry, Supplier<Void> onAllRetriesFailed) {
        this.retry = retry;
        setupAdvancedEventHandlers(onAllRetriesFailed);
    }

    private void setupAdvancedEventHandlers(Supplier<Void> onAllRetriesFailed) {
        Retry.EventPublisher eventPublisher = retry.getEventPublisher();

        eventPublisher.onRetry(event -> {
            // Логируем каждую попытку retry
            logRetryAttempt(event);

            // Отправляем уведомление
            sendRetryAlert(event);
        });

        // Действия после успешного retry
        eventPublisher.onSuccess(this::handleRetrySuccess);

        // Действия после исчерпания всех попыток
        eventPublisher.onError(event -> {
            onAllRetriesFailed.get();
        });
    }

    public <T> Mono<T> withRetry(
        Mono<T> mono,
        String operationName
    ) {
        System.out.println("PRINTANI TUPA --- " + retry);
        return mono
            .transformDeferred(RetryOperator.of(retry))
            .doOnSubscribe(subscription -> {
                System.out.println("🚀 Starting operation: " + operationName);
            })
            .doOnSuccess(result -> {
                System.out.println("✅ Operation completed: " + operationName);
            });
    }

    private void logRetryAttempt(RetryOnRetryEvent event) {
        System.out.printf("🔄 Retry #%d for %s. Wait time: %dms%n",
            event.getNumberOfRetryAttempts(),
            event.getName(),
            event.getWaitInterval().toMillis());
    }

    private void sendRetryAlert(RetryOnRetryEvent event) {
        // Отправляем alert в систему мониторинга
        System.out.println("📢 Retry alert: " + event.getName() +
            " attempt #" + event.getNumberOfRetryAttempts());
    }

    private void handleRetrySuccess(RetryOnSuccessEvent event) {
        System.out.println("🎉 Retry succeeded for " + event.getName() +
            " after " + event.getNumberOfRetryAttempts() + " attempts");
    }

    private void handleRetryExhausted(RetryOnErrorEvent event) {
        System.err.println("💥 Retry exhausted for " + event.getName() +
            " after " + event.getNumberOfRetryAttempts() + " attempts");

        // Можно отправить critical alert
        sendCriticalAlert(event);
    }

    private void sendCriticalAlert(RetryOnErrorEvent event) {
        System.out.println("🚨 CRITICAL: All retries failed for " + event.getName());
    }
}
