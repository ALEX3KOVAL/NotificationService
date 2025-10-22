package ru.alex3koval.notificationService.configuration.di.appImplModule.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventingContract.Event;
import ru.alex3koval.eventingContract.vo.EventStatus;
import ru.alex3koval.eventingImpl.manager.EventListenerManager;
import ru.alex3koval.notificationService.domain.common.repository.EventRepository;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class KafkaClientConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event> kafkaListenerContainerFactory(
        ConsumerFactory<String, Event> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean("onEventHasBeenProcessedHandler")
    Function<String, Mono<?>> onEventHasBeenProcessedHandler(
        EventRepository<Long> transactionalOutboxRepository
    ) {
        return id -> transactionalOutboxRepository.updateStatus(
            Long.parseLong(id),
            EventStatus.CONFIRMED
        );
    }

    @Bean
    EventListenerManager eventListenerManager(
        ConcurrentKafkaListenerContainerFactory<String, Event> containerFactory,
        ObjectMapper objectMapper,
        @Qualifier("onEventHasBeenProcessedHandler") Function<String, Mono<?>> onEventHasBeenProcessedHandler
    ) {
        return new EventListenerManager(containerFactory, objectMapper, onEventHasBeenProcessedHandler);
    }
}
