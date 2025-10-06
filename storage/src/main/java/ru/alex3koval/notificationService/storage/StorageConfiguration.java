package ru.alex3koval.notificationService.storage;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import ru.alex3koval.notificationService.storage.converter.sendingRecipient.ReadingSendingRecipientConverter;
import ru.alex3koval.notificationService.storage.converter.sendingRecipient.WritingSendingRecipientConverter;
import ru.alex3koval.notificationService.storage.converter.sendingStatus.ReadingSendingStatusConverter;
import ru.alex3koval.notificationService.storage.converter.sendingStatus.WritingSendingStatusConverter;

import java.util.List;

@Configuration
public class StorageConfiguration {
    @Bean
    public R2dbcDialect dialect() {
        return PostgresDialect.INSTANCE;
    }

    @Bean
    R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(R2dbcDialect dialect) {
        List<Converter<?, ?>> converters = List.of(
            new WritingSendingStatusConverter(),
            new ReadingSendingStatusConverter(),

            new WritingSendingRecipientConverter(),
            new ReadingSendingRecipientConverter(),

            new WritingSendingStatusConverter(),
            new ReadingSendingStatusConverter()
        );

        return R2dbcCustomConversions.of(dialect, converters);
    }
}
