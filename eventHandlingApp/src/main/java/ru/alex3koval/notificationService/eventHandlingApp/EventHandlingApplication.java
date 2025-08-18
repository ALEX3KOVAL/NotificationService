package ru.alex3koval.notificationService.eventHandlingApp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.alex3koval.notificationService.eventHandlingApp.configuration.EventHandlingAppConfiguration;

public class EventHandlingApplication {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(EventHandlingAppConfiguration.class);

        while(true) {}
    }
}
