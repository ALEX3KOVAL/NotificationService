package ru.alex3koval.eventing.impl;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.alex3koval.eventing.contract.EventPusher;
import ru.alex3koval.eventing.impl.factory.AmqpTemplateFactory;
import ru.alex3koval.eventing.impl.factory.EventPusherFactory;
import ru.alex3koval.eventing.impl.factory.MessageListenerContainerFactory;
import ru.alex3koval.eventing.impl.manager.ListenerManager;
import ru.alex3koval.eventing.impl.vo.BrokerType;

@Configuration
public class EventingConfiguration {
    @Bean
    AmqpEnvironment amqpEnvironment() {
        return new AmqpEnvironment();
    }

    @Bean
    ListenerManager listenerManager(MessageListenerContainerFactory messageListenerContainerFactory) {
        return new ListenerManager(messageListenerContainerFactory);
    }

    @Bean
    BrokerType brokerType(AmqpEnvironment amqpEnvironment) {
        return amqpEnvironment.getBrokerType();
    }

    @Bean
    AmqpTemplateFactory amqpTemplateFactory(
        ConnectionFactory connectionFactory,
        MessageConverter converter
    ) {
        return new AmqpTemplateFactory(
            connectionFactory,
            converter
        );
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue queue(AmqpEnvironment amqpEnvironment) {
        return new Queue(
            amqpEnvironment.getQueueName(),
            false
        );
    }

    /*@Bean
    RabbitAdmin rabbitAdmin(Queue queue, ConnectionFactory connectionFactory) {
        RabbitAdmin rbtAdmin = new RabbitAdmin(connectionFactory);

        rbtAdmin.declareQueue(queue);

        return rbtAdmin;
    }*/

    @Bean
    EventPusherFactory eventPusherFactory(AmqpTemplateFactory amqpTemplateFactory, BrokerType brokerType) {
        AmqpTemplate amqpTemplate = amqpTemplateFactory.create(brokerType);

        return new EventPusherFactory(amqpTemplate);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    EventPusher eventPusher(EventPusherFactory eventPusherFactory) {
        return eventPusherFactory.create();
    }
}
