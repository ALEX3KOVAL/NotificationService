package ru.alex3koval.notificationService.server.configuration;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.notificationService.configuration.di.AppCommonConfiguration;

@Configuration
@Import({AppCommonConfiguration.class, HttpBodyDeserializerConfiguration.class})
@ComponentScan("ru.alex3koval.notificationService.storage")
public class MainConfiguration {
    @Bean
    @Qualifier("otpRetry")
    Retry otpRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("otpRetry");
    }
}
