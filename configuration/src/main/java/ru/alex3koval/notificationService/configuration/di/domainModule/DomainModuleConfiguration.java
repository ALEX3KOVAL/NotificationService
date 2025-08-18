package ru.alex3koval.notificationService.configuration.di.domainModule;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    DomainRepositoryConfiguration.class,
    DomainServiceConfiguration.class,
    DomainCommandConfiguration.class
})
public class DomainModuleConfiguration {
}
