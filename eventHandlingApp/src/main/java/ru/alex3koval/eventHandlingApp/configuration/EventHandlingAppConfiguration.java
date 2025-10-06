package ru.alex3koval.eventHandlingApp.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean
    @Qualifier("consoleLogger")
    Logger logger() {
        return LoggerFactory.getLogger("CONSOLE");
    }
}
