package ru.alex3koval.notificationService.configuration.di.appImplModule.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.alex3koval.notificationService.configuration.AppEnvironment;

@Configuration
@RequiredArgsConstructor
public class HttpClientConfiguration {
    @Bean("fileServiceWebClientProps")
    AppEnvironment.WebClientProps fileServiceWebClientProps(AppEnvironment appEnv) {
        return appEnv.webClient().get("fileService");
    }

    @Bean("fileServiceWebClient")
    WebClient fileServiceWebClient(
        @Qualifier("fileServiceWebClientProps") AppEnvironment.WebClientProps fileServiceWebClientProps
    ) {
        return WebClient
            .builder()
            .baseUrl(
                String.format(
                    "%s://%s:%d",
                    fileServiceWebClientProps.protocol(),
                    fileServiceWebClientProps.host(),
                    fileServiceWebClientProps.port()
                )
            )
            .build();
    }
}
