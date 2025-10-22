package ru.alex3koval.eventProducerApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.eventingContract.dto.EventRDTO;
import ru.alex3koval.transactionalOutBox.TransactionalOutBoxConfiguration;
import ru.alex3koval.transactionalOutBox.serialization.EventRdtoDeserializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Import(TransactionalOutBoxConfiguration.class)
public class EventProducerAppConfiguration {
    @Bean("cdcEventHandlerExecutorService")
    ExecutorService cdcEventHandlerExecutor() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();

        module.addDeserializer(EventRDTO.class, new EventRdtoDeserializer());

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        mapper.registerModule(module);

        return mapper;
    }
}
