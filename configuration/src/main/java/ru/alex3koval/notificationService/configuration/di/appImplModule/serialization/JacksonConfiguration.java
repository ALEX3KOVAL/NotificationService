package ru.alex3koval.notificationService.configuration.di.appImplModule.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfiguration {
    @Bean
    @Primary
    ObjectMapper objectMapper(
        @Qualifier("domainVoSerializationModule") SimpleModule domainVoSerializationModule,
        @Qualifier("domainEventSerializationModule") SimpleModule domainEventSerializationModule
    ) {
        ObjectMapper objMapper = new ObjectMapper();

        objMapper.registerModule(new JavaTimeModule());
        objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        objMapper.registerModule(domainVoSerializationModule);
        objMapper.registerModule(domainEventSerializationModule);

        return objMapper;
    }
}
