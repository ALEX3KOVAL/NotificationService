package ru.alex3koval.eventProducerApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.transactionalOutBox.TransactionalOutBoxConfiguration;

@Configuration
@Import(TransactionalOutBoxConfiguration.class)
public class EventProducerAppConfiguration {
    @Bean
    @Qualifier("consoleLogger")
    Logger logger() {
        return LoggerFactory.getLogger("CONSOLE");
    }
}
