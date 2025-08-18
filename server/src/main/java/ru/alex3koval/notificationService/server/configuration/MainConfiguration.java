package ru.alex3koval.notificationService.server.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.notificationService.configuration.di.AppCommonConfiguration;

@Configuration
@Import(AppCommonConfiguration.class)
@ComponentScan("ru.alex3koval.notificationService.storage")
public class MainConfiguration {
}
