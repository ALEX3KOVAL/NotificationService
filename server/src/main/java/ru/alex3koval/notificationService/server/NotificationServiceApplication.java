package ru.alex3koval.notificationService.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.alex3koval.notificationService.configuration.AppEnvironment;


@SpringBootApplication
@EnableConfigurationProperties(AppEnvironment.class)
@EnableAspectJAutoProxy
public class NotificationServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(NotificationServiceApplication.class)
            .web(WebApplicationType.REACTIVE)
            .run(args);
    }
}
