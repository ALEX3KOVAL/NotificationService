package ru.alex3koval.notificationService.server.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpMailRequest;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpViaPhoneRequest;
import ru.alex3koval.notificationService.server.api.otp.dto.request.deserializer.SendMailRequestDeserializer;
import ru.alex3koval.notificationService.server.api.otp.dto.request.deserializer.SendOtpViaPhoneRequestDeserializer;
import ru.alex3koval.notificationService.server.core.RegisterHttpBodySerializationModuleApplicationRunner;

@Configuration
public class HttpBodyDeserializerConfiguration {
    @Bean
    @Qualifier("httpBodyDeserializerModule")
    Module httpBodyDeserializers() {
        SimpleModule module = new SimpleModule();

        module.addDeserializer(SendOtpViaPhoneRequest.class, new SendOtpViaPhoneRequestDeserializer());
        module.addDeserializer(SendOtpMailRequest.class, new SendMailRequestDeserializer());

        return module;
    }

    @Bean
    RegisterHttpBodySerializationModuleApplicationRunner registerCustomDeserializerModuleApplicationRunner(
        ObjectMapper objectMapper,
        @Qualifier("httpBodyDeserializerModule") Module httpBodyDeserializers
    ) {
        return new RegisterHttpBodySerializationModuleApplicationRunner(objectMapper, httpBodyDeserializers);
    }
}
