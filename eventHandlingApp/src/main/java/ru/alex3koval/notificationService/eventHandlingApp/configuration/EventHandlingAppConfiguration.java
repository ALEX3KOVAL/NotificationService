package ru.alex3koval.notificationService.eventHandlingApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.notificationService.eventHandlingApp.settings.ListenersRegistrationProps;
import ru.alex3koval.notificationService.common.configuration.CommonModuleConfiguration;
import ru.alex3koval.notificationService.configuration.di.AppCommonConfiguration;
import ru.alex3koval.kafkaEventer.EventerConfiguration;
import ru.alex3koval.kafkaEventer.ListenersRegistrationConfig;

@Configuration
@Import({
    AppCommonConfiguration.class,
    EventerConfiguration.class,
    EventListenerConfiguration.class,
    CommonModuleConfiguration.class
})
public class EventHandlingAppConfiguration {
    @Bean
    ListenersRegistrationProps listenersRegistrationProps() {
        return new ListenersRegistrationProps();
    }

    @Bean
    ListenersRegistrationConfig listenersRegistrationConfig(
        ListenersRegistrationProps listenersRegistrationProps
    ) {
        return new ListenersRegistrationConfig(listenersRegistrationProps.getAllProps());
    }
}
