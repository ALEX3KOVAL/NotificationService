package ru.alex3koval.notificationService.configuration.di.appImplModule.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alex3koval.notificationService.appImpl.core.serialization.event.deser.TemplatedMailSendingHasBeenRequestedEventDeserializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.event.deser.TestEventDeserializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.event.ser.TemplatedMailSendingHasBeenRequestedEventSerializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.event.ser.TestEventSerializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.email.EmailDeserializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.email.EmailSerializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.identifier.IdentifierDeserializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.identifier.IdentifierSerializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.mailFormat.MailFormatDeserializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.mailFormat.MailFormatSerializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.otpReason.OtpReasonDeserializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.otpReason.OtpReasonSerializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.phone.PhoneDeserializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.phone.PhoneSerializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.sendingReason.SendingReasonDeserializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.sendingReason.SendingReasonSerializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.topic.TopicDeserializer;
import ru.alex3koval.notificationService.appImpl.core.serialization.vo.topic.TopicSerializer;
import ru.alex3koval.notificationService.domain.common.event.TemplatedMailSendingHasBeenRequestedEvent;
import ru.alex3koval.notificationService.domain.common.event.TestEvent;
import ru.alex3koval.notificationService.domain.common.vo.Topic;
import ru.alex3koval.notificationService.domain.vo.*;

@Configuration
public class SerializationConfiguration {
    @Bean("domainVoSerializationModule")
    SimpleModule domainVoSerializationModule() {
        SimpleModule module = new SimpleModule();

        module.addSerializer(Identifier.class, new IdentifierSerializer());
        module.addDeserializer(Identifier.class, new IdentifierDeserializer());

        module.addSerializer(Email.class, new EmailSerializer());
        module.addDeserializer(Email.class, new EmailDeserializer());

        module.addSerializer(Phone.class, new PhoneSerializer());
        module.addDeserializer(Phone.class, new PhoneDeserializer());

        module.addSerializer(OtpReason.class, new OtpReasonSerializer());
        module.addDeserializer(OtpReason.class, new OtpReasonDeserializer());

        module.addSerializer(SendingReason.class, new SendingReasonSerializer());
        module.addDeserializer(SendingReason.class, new SendingReasonDeserializer());

        module.addSerializer(MailFormat.class, new MailFormatSerializer());
        module.addDeserializer(MailFormat.class, new MailFormatDeserializer());

        module.addSerializer(Topic.class, new TopicSerializer());
        module.addDeserializer(Topic.class, new TopicDeserializer());

        return module;
    }

    @Bean("domainEventSerializationModule")
    SimpleModule domainEventSerializationModule() {
        SimpleModule module = new SimpleModule();

        module.addSerializer(
            TemplatedMailSendingHasBeenRequestedEvent.class,
            new TemplatedMailSendingHasBeenRequestedEventSerializer()
        );
        module.addDeserializer(
            TemplatedMailSendingHasBeenRequestedEvent.class,
            new TemplatedMailSendingHasBeenRequestedEventDeserializer()
        );

        module.addSerializer(
            TestEvent.class,
            new TestEventSerializer()
        );
        module.addDeserializer(
            TestEvent.class,
            new TestEventDeserializer()
        );

        return module;
    }
}
