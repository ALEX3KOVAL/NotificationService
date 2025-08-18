package ru.alex3koval.notificationService.eventHandlingApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.notificationService.configuration.di.AppCommonConfiguration;
import ru.alex3koval.notificationService.eventHandlingApp.appHook.ApplicationContextRefreshedListener;

@Configuration
@Import({
    AppCommonConfiguration.class,
    EventListenerConfiguration.class
})
public class EventHandlingAppConfiguration {
    @Bean
    ApplicationContextRefreshedListener applicationContextRefreshedListener() {
        return new ApplicationContextRefreshedListener();
    }
}
