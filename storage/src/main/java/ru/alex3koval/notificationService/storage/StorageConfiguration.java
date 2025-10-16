package ru.alex3koval.notificationService.storage;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import ru.alex3koval.notificationService.storage.converter.eventStatus.ReadingEventStatusConverter;
import ru.alex3koval.notificationService.storage.converter.eventStatus.WritingEventStatusConverter;
import ru.alex3koval.notificationService.storage.converter.mailFormat.ReadingMailFormatConverter;
import ru.alex3koval.notificationService.storage.converter.mailFormat.WritingMailFormatConverter;
import ru.alex3koval.notificationService.storage.converter.sendingReason.ReadingSendingReasonConverter;
import ru.alex3koval.notificationService.storage.converter.sendingReason.WritingSendingReasonConverter;
import ru.alex3koval.notificationService.storage.converter.sendingRecipient.ReadingSendingRecipientConverter;
import ru.alex3koval.notificationService.storage.converter.sendingRecipient.WritingSendingRecipientConverter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableR2dbcRepositories("ru.alex3koval.notificationService.storage.repository.orm")
@EntityScan("ru.alex3koval.notificationService.storage.entity")
@EnableR2dbcAuditing
public class StorageConfiguration {
    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = Arrays.asList(
            new WritingSendingRecipientConverter(),
            new ReadingSendingRecipientConverter(),

            new WritingSendingReasonConverter(),
            new ReadingSendingReasonConverter(),

            new WritingEventStatusConverter(),
            new ReadingEventStatusConverter(),

            new WritingMailFormatConverter(),
            new ReadingMailFormatConverter()
        );

        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters);
    }

    @Bean
    DbCheckAppRunner dbCheckAppRunner(ConnectionFactory connectionFactory) {
        return new DbCheckAppRunner(connectionFactory);
    }
}
