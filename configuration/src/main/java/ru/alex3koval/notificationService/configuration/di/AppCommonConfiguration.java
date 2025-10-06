package ru.alex3koval.notificationService.configuration.di;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.eventingImpl.EventingConfiguration;
import ru.alex3koval.notificationService.configuration.di.appImplModule.AppImplModuleConfiguration;
import ru.alex3koval.notificationService.configuration.di.domainModule.DomainModuleConfiguration;
import ru.alex3koval.notificationService.storage.StorageConfiguration;

@Configuration
@Import({
    DomainModuleConfiguration.class,
    AppImplModuleConfiguration.class,
    StorageConfiguration.class,
    EventingConfiguration.class,
    TypeDependentConfiguration.class
})
@RequiredArgsConstructor
public class AppCommonConfiguration {
}
