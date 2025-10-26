package ru.alex3koval.notificationService.configuration.di.appImplModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import ru.alex3koval.notificationService.appImpl.factory.RetryServiceFactory;
import ru.alex3koval.notificationService.appImpl.model.RetryConfigurations;
import ru.alex3koval.notificationService.appImpl.model.SmsMessageBodyProperties;
import ru.alex3koval.notificationService.appImpl.service.RetryService;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.notificationService.configuration.di.appImplModule.client.HttpClientConfiguration;
import ru.alex3koval.notificationService.configuration.di.appImplModule.client.KafkaClientConfiguration;
import ru.alex3koval.notificationService.configuration.di.appImplModule.serialization.JacksonConfiguration;
import ru.alex3koval.notificationService.configuration.di.appImplModule.serialization.SerializationConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Import({
    KafkaClientConfiguration.class,
    JacksonConfiguration.class,
    SerializationConfiguration.class,
    HttpClientConfiguration.class
})
public class AppImplModuleConfiguration {
    private final AppEnvironment appEnv;

    @Bean
    SmsMessageBodyProperties SmsMessageBodyProperties(ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(
            Files.readString(
                Paths.get(appEnv.sms().otpTemplateJsonFilePath())
            ),
            SmsMessageBodyProperties.class
        );
    }

    @Bean
    RetryConfigurations retryConfigProperties() {
        return new RetryConfigurations(
            appEnv
                .retry()
                .entrySet()
                .stream()
                .map(entry -> {
                    AppEnvironment.RetryProps props = entry.getValue();

                    return new AbstractMap.SimpleEntry<>(
                        entry.getKey(),
                        new RetryConfigurations.Props(props.maxAttempts(), props.minDelay(), props.jitter())
                    );
                })
                .collect(
                    Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)
                )
        );
    }

    @Bean
    RetryServiceFactory retryServiceFactory(RetryConfigurations retryConfigs) {
        return new RetryServiceFactory(retryConfigs);
    }

    @Bean
    FreeMarkerConfig freemarkerClassLoaderConfig(AppEnvironment appEnv) throws IOException, TemplateException {
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

    @Bean("otpRetry")
    @Scope("prototype")
    RetryService otpRetryService(RetryServiceFactory retryServiceFactory) {
        return retryServiceFactory.create("otp");
    }

    @Bean("fileServiceRetry")
    @Scope("prototype")
    RetryService fileServiceRetryService(RetryServiceFactory retryServiceFactory) {
        return retryServiceFactory.create("file-service");
    }
}
