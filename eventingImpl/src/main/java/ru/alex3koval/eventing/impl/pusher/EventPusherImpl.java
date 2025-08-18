package ru.alex3koval.eventing.impl.pusher;

import org.springframework.amqp.core.AmqpTemplate;
import ru.alex3koval.eventing.contract.Event;
import ru.alex3koval.eventing.contract.EventPusher;

public record EventPusherImpl(AmqpTemplate template) implements EventPusher {
    @Override
    public void push(Event event) throws InterruptedException {
        Thread.sleep(3000);

        template.convertAndSend(event);
    }
}
