package ru.alex3koval.notificationService.domain.service;

import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public interface FileServiceFacade {
    Flux<Map.Entry<String, ByteArrayInputStream>> getAllByAttachmentUrls(List<String> attachmentUrls);
}
