package ru.alex3koval.notificationService.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public record AppEnvironment(
    Mailer mailer,
    AMQP amqp
) {
    public record Mailer(
        String host,
        Byte port,
        String senderAddress,
        String username,
        String password,
        String protocol,
        Short maxOfAttempts,
        String otpTemplateFolderPath,
        String otpTemplateName
    ) {
    }

    public record AMQP(
        String queueName,
        Connection conn
    ) {
        public record Connection(
            String username,
            String password
        ) {
        }
    }
}
