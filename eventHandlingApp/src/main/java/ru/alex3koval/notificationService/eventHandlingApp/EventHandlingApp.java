package ru.alex3koval.notificationService.eventHandlingApp;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.kafkaEventer.EventerProperties;

@SpringBootApplication
@EnableConfigurationProperties({EventerProperties.class, AppEnvironment.class})
public class EventHandlingApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EventHandlingApp.class)
            .web(WebApplicationType.NONE)
            .run(args);
    }
}
