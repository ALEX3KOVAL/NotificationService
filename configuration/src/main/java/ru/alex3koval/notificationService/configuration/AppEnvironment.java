package ru.alex3koval.notificationService.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public record AppEnvironment(
    Mailer mailer,
    Sms sms
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
}
