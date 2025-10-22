package ru.alex3koval.notificationService.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CheckerConfiguration.class)
public class CommonModuleConfiguration {
}
