package ru.alex3koval.notificationService.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import ru.alex3koval.notificationService.common.checker.TopicsCheckerAppRunner;
import ru.alex3koval.eventingImpl.factory.KafkaTopicsFetcherFactory;

@Configuration
public class CheckerConfiguration {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    TopicsCheckerAppRunner topicsCheckerAppRunner(
        KafkaTopicsFetcherFactory kafkaTopicsFetcherFactory
    ) {
        return new TopicsCheckerAppRunner(kafkaTopicsFetcherFactory);
    }
}
