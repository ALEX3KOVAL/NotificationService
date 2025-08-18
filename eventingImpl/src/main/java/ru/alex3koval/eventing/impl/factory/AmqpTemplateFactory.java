package ru.alex3koval.eventing.impl.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import ru.alex3koval.eventing.impl.vo.BrokerType;

@RequiredArgsConstructor
public class AmqpTemplateFactory {
    private final ConnectionFactory connectionFactory;
    private final MessageConverter converter;

    public AmqpTemplate create(BrokerType brokerType) {
        return switch (brokerType) {
            case BrokerType.RABBIT_MQ -> {
                RabbitTemplate template = new RabbitTemplate(connectionFactory);

                template.setMessageConverter(converter);

                yield template;
            }
            default -> throw new IllegalArgumentException("Тип брокера не поддерживается: " + brokerType);
        };
    }
}
