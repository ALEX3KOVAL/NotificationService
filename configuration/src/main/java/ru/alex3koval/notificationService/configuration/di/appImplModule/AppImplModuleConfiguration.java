package ru.alex3koval.notificationService.configuration.di.appImplModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.notificationService.appImpl.factory.RetryServiceFactory;
import ru.alex3koval.notificationService.appImpl.model.SmsMessageBodyProperties;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.configuration.di.appImplModule.client.KafkaClientConfiguration;
import ru.alex3koval.notificationService.configuration.di.appImplModule.serialization.JacksonConfiguration;
import ru.alex3koval.notificationService.configuration.di.appImplModule.serialization.SerializationConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
@Import({KafkaClientConfiguration.class, JacksonConfiguration.class, SerializationConfiguration.class})
public class AppImplModuleConfiguration {
    @Bean
    SmsMessageBodyProperties SmsMessageBodyProperties(
        ObjectMapper objectMapper,
        AppEnvironment appEnv
    ) throws IOException {
        return objectMapper.readValue(
            Files.readString(
                Paths.get(appEnv.sms().otpTemplateJsonFilePath())
            ),
            SmsMessageBodyProperties.class
        );
    }

    @Bean
    @Qualifier("otpRetry")
    Retry otpRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("otpRetry");
    }

    @Bean
    RetryServiceFactory retryServiceFactory()
    {
        return new RetryServiceFactory();
    }

    @Bean
    @Qualifier("consoleLogger")
    Logger logger() {
        return LoggerFactory.getLogger("CONSOLE");
    }
}
