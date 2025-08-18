package ru.alex3koval.eventing.impl.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import ru.alex3koval.eventing.contract.EventPusher;
import ru.alex3koval.eventing.impl.pusher.EventPusherImpl;

@RequiredArgsConstructor
public class EventPusherFactory {
    private final AmqpTemplate amqpTemplate;

    public EventPusher create() {
        return new EventPusherImpl(amqpTemplate);
    }
}
