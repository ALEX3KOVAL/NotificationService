package ru.alex3koval.notificationService.server.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.configuration.di.AppCommonConfiguration;

import java.io.File;
import java.io.IOException;

@Configuration
@Import({AppCommonConfiguration.class, HttpBodyDeserializerConfiguration.class})
@ComponentScan("ru.alex3koval.notificationService.storage")
@EnableWebFlux
@RequiredArgsConstructor
public class MainConfiguration implements WebFluxConfigurer {
    private final ObjectMapper objectMapper;

    @Bean
    FreeMarkerConfig freemarkerClassLoaderConfig(
        AppEnvironment appEnv
    ) throws IOException, TemplateException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);

        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        configurer.setTemplateLoaderPath("file:" + appEnv.mailer().otpTemplateFolderPath() + "/");
        configurer.setPreferFileSystemAccess(true);

        MultiTemplateLoader multiLoader = new MultiTemplateLoader(
            new TemplateLoader[] { new FileTemplateLoader(new File(appEnv.mailer().otpTemplateFolderPath())) }
        );

        configuration.setTemplateLoader(multiLoader);

        configurer.setConfiguration(configuration);
        configurer.afterPropertiesSet();

        return configurer;
    }

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
