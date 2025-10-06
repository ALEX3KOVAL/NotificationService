package ru.alex3koval.notificationService.configuration.di.domainModule;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories("ru.alex3koval.notificationService.storage.repository.orm")
@EnableR2dbcAuditing
@EntityScan("ru.alex3koval.notificationService.storage.entity")
public class DomainRepositoryConfiguration {
}
