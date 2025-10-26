package ru.alex3koval.notificationService.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("app")
public record AppEnvironment(
    Mailer mailer,
    Sms sms,
    Map<String, RetryProps> retry,
    Map<String, WebClientProps> webClient
) {
    public record Mailer(
        String senderAddress,
        Short maxOfAttempts,
        String otpTemplateFolderPath,
        String otpTemplateName
    ) {
    }

    public record Sms(
        String otpTemplateJsonFilePath
    ) {
    }

    public record RetryProps(
        short maxAttempts,
        long minDelay,
        float jitter
    ) {
    }

    public record WebClientProps(
        String protocol,
        String host,
        Integer port
    ) {
    }
}
