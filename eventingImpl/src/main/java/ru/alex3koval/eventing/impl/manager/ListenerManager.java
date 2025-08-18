package ru.alex3koval.eventing.impl.manager;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import ru.alex3koval.eventing.impl.factory.MessageListenerContainerFactory;

import java.util.HashMap;
import java.util.Map;

public class ListenerManager {
    private final Map<String, SimpleMessageListenerContainer> containers = new HashMap<>();
    private final MessageListenerContainerFactory messageListenerContainerFactory;

    public ListenerManager(MessageListenerContainerFactory messageListenerContainerFactory) {
        this.messageListenerContainerFactory = messageListenerContainerFactory;
    }

    public void registerListener(
        String queueName,
        MessageListener listener,
        int amountOfConsumers
    ) {
        SimpleMessageListenerContainer container = messageListenerContainerFactory.create(
            queueName,
            listener,
            amountOfConsumers
        );

        container.start();

        containers.put(queueName, container);
    }

    public void unregisterListener(String queueName) {
        SimpleMessageListenerContainer container = containers.remove(queueName);
        if (container != null) {
            container.stop();
            container.destroy();
        }
    }
}
