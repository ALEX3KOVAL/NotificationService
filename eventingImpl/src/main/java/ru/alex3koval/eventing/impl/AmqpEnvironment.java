package ru.alex3koval.eventing.impl;

import lombok.Getter;
import ru.alex3koval.eventing.impl.vo.BrokerType;
import java.util.Optional;

@Getter
public class AmqpEnvironment {
    private final String queueName;
    private final Connection conn;
    private final BrokerType brokerType;

    AmqpEnvironment() {
        this.brokerType = BrokerType.valueOf(getEnvValue("APP_AMQP_BROKER_TYPE"));
        this.queueName = getEnvValue("APP_AMQP_QUEUE_NAME");
        this.conn = new Connection(
            getEnvValue("APP_AMQP_CONNECTION_USERNAME"),
            getEnvValue("APP_AMQP_CONNECTION_PASSWORD")
        );
    }

    public record Connection(
        String username,
        String password
    ) {
    }

    private String getEnvValue(String key) {
        return Optional
            .ofNullable(System.getenv(key))
            .orElseThrow();
    }
}
