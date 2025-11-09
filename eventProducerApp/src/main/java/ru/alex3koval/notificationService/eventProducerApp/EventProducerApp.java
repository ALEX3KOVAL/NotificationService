package ru.alex3koval.notificationService.eventProducerApp;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.alex3koval.transactionalOutBox.CdcProperties;

@SpringBootApplication
@EnableConfigurationProperties(CdcProperties.class)
public class EventProducerApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EventProducerApp.class)
            .web(WebApplicationType.NONE)
            .run(args);
    }
}
