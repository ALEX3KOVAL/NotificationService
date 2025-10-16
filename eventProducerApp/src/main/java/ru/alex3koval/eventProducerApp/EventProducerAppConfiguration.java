package ru.alex3koval.eventProducerApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.alex3koval.transactionalOutBox.TransactionalOutBoxConfiguration;

@Configuration
@Import(TransactionalOutBoxConfiguration.class)
public class EventProducerAppConfiguration {
}
