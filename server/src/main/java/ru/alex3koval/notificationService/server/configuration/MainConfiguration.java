package ru.alex3koval.notificationService.server.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import ru.alex3koval.notificationService.configuration.di.AppCommonConfiguration;

@Configuration
@Import({AppCommonConfiguration.class, HttpBodyDeserializerConfiguration.class})
@ComponentScan("ru.alex3koval.notificationService.storage")
@EnableWebFlux
@RequiredArgsConstructor
public class MainConfiguration implements WebFluxConfigurer {
    private final ObjectMapper objectMapper;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker();
    }

    @Override
    public void configureHttpMessageCodecs(
        ServerCodecConfigurer configurer
    ) {
        configurer.defaultCodecs().jackson2JsonEncoder(
            new Jackson2JsonEncoder(objectMapper)
        );
        configurer.defaultCodecs().jackson2JsonDecoder(
            new Jackson2JsonDecoder(objectMapper)
        );
    }
}
