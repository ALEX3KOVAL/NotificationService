package ru.alex3koval.notificationService.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.alex3koval.notificationService.configuration.AppEnvironment;

import java.util.List;


@SpringBootApplication
@EnableConfigurationProperties(AppEnvironment.class)
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    public static <T> void add(List<? super T> s, T e) {
        s.add(e);
    }
}
