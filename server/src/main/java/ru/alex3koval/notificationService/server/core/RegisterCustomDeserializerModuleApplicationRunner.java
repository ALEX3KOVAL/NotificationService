package ru.alex3koval.notificationService.server.core;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@RequiredArgsConstructor
public class RegisterCustomDeserializerModuleApplicationRunner implements ApplicationRunner {
    private final ObjectMapper mapper;
    private final Module registerCustomDeserializerModule;

    @Override
    public void run(ApplicationArguments args) {
        mapper.registerModule(registerCustomDeserializerModule);
    }
}
