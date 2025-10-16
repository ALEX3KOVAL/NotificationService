package ru.alex3koval.eventHandlingApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.eventHandlingApp.EventHandlingAppRunner;
import ru.alex3koval.kafkaEventer.EventerConfiguration;
import ru.alex3koval.kafkaEventer.EventerPreprocessor;
import ru.alex3koval.notificationService.configuration.di.AppCommonConfiguration;

@Configuration
@Import({
    AppCommonConfiguration.class,
    EventerConfiguration.class
})
public class EventHandlingAppConfiguration {
    @Bean
    EventHandlingAppRunner eventerAppRunner(
        EventerPreprocessor eventerPreprocessor
    ) {
        return new EventHandlingAppRunner(eventerPreprocessor);
    }
}
