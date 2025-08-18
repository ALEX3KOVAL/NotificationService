package ru.alex3koval.notificationService.server.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpMailRequest;
import ru.alex3koval.notificationService.server.api.otp.dto.request.SendOtpViaPhoneRequest;
import ru.alex3koval.notificationService.server.api.otp.dto.request.deserializer.SendMailRequestDeserializer;
import ru.alex3koval.notificationService.server.api.otp.dto.request.deserializer.SendOtpViaPhoneRequestDeserializer;

@Configuration
public class JacksonConfiguration {
    @Bean
    Module customDeserializers() {
        SimpleModule module = new SimpleModule();

        module.addDeserializer(SendOtpViaPhoneRequest.class, new SendOtpViaPhoneRequestDeserializer());
        module.addDeserializer(SendOtpMailRequest.class, new SendMailRequestDeserializer());

        return module;
    }
}
