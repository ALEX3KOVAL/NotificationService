package ru.alex3koval.notificationService.configuration.di.appImplModule;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;

@Configuration
@RequiredArgsConstructor
@Import({JacksonConfiguration.class})
public class AppImplModuleConfiguration {
    @Bean
    public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();

        configurer.setDefaultEncoding("UTF-8");

        return configurer;
    }
}
