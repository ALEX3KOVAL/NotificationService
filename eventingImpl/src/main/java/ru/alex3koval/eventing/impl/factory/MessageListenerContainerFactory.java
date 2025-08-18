package ru.alex3koval.eventing.impl.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

@RequiredArgsConstructor
public class MessageListenerContainerFactory {
    private final ConnectionFactory connectionFactory;
    /*private final RabbitAdmin rabbitAdmin;
    private final Exchange fanoutExchange;
    private final Binding binding;*/

    public SimpleMessageListenerContainer create(
        String queueName,
        MessageListener listener,
        int amountOfConsumers
    ) {
        /*rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);*/

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setConcurrentConsumers(amountOfConsumers);
        container.setExposeListenerChannel(true);
        container.setDefaultRequeueRejected(false);
        container.setPrefetchCount(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(listener);

        return container;
    }
}
