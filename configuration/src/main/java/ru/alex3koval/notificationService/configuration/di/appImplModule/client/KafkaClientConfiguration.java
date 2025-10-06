package ru.alex3koval.notificationService.configuration.di.appImplModule.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.alex3koval.eventingContract.Event;
import ru.alex3koval.eventingImpl.manager.EventListenerManager;

@Configuration
@RequiredArgsConstructor
public class KafkaClientConfiguration {
    @Bean
    ConsumerFactory<String, Event> consumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(
            kafkaProperties.buildConsumerProperties(),
            new StringDeserializer(),
            new JsonDeserializer<>()
        );
    }

    @Bean
    KafkaAdmin kafkaAdmin(KafkaProperties kafkaProperties) {
        return new KafkaAdmin(
            kafkaProperties.buildAdminProperties(null)
        );
    }

    @Bean
    AdminClient adminClient(KafkaAdmin kafkaAdmin) {
        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Bean
    ProducerFactory<String, Event> producerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(
            kafkaProperties.buildProducerProperties(),
            new StringSerializer(),
            new JsonSerializer<>()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event> kafkaListenerContainerFactory(
        ConsumerFactory<String, Event> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    EventListenerManager eventListenerManager(
        ConcurrentKafkaListenerContainerFactory<String, Event> containerFactory,
        ObjectMapper objectMapper
    ) {
        return new EventListenerManager(containerFactory, objectMapper);
    }
}
