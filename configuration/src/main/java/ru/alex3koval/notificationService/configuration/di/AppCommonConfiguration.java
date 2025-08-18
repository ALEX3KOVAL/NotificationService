package ru.alex3koval.notificationService.configuration.di;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.eventing.impl.EventingConfiguration;
import ru.alex3koval.notificationService.configuration.di.appImplModule.AppImplModuleConfiguration;
import ru.alex3koval.notificationService.configuration.di.domainModule.DomainModuleConfiguration;

@Configuration
@Import({
    DomainModuleConfiguration.class,
    AppImplModuleConfiguration.class,
    EventingConfiguration.class
})
@RequiredArgsConstructor
public class AppCommonConfiguration {
}
