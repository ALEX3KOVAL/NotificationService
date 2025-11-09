package ru.alex3koval.notificationService.appImpl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;
import ru.alex3koval.notificationService.domain.service.FileServiceFacade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class FileServiceFacadeImpl implements FileServiceFacade {
    private final WebClient webClient;

    @Override
    public Flux<Map.Entry<String, ByteArrayInputStream>> getAllByAttachmentUrls(List<String> attachmentUrls) {
        Map<String, ByteArrayOutputStream> outStreams = attachmentUrls
            .stream()
            .map(url -> new AbstractMap.SimpleEntry<>(url, new ByteArrayOutputStream()))
            .collect(
                Collectors.toMap(
                    AbstractMap.SimpleEntry::getKey,
                    AbstractMap.SimpleEntry::getValue
                )
            );

        List<Mono<Map.Entry<String, ByteArrayOutputStream>>> streams = outStreams
            .entrySet()
            .stream()
            .map(entry ->
                webClient
                    .get()
                    .uri(uriBuilder ->
                        uriBuilder
                            .queryParam("url", entry.getKey()).build()
                    )
                    .accept(MediaType.APPLICATION_PDF)
                    .exchangeToFlux(response -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            return withFilenameContext(
                                response.bodyToFlux(DataBuffer.class),
                                extractFilenameFromHeaders(response.headers().asHttpHeaders())
                            );
                        }

                        return response
                            .<DataBuffer>createError()
                            .flux();
                    })
                    .as(bufferFlux -> DataBufferUtils.write(bufferFlux, entry.getValue()))
                    .then(
                        Mono.deferContextual(ctx ->
                            Mono.just(
                                Map.entry((String) ctx.get("filename"), entry.getValue())
                            )
                        )
                    )
            )
            .toList();

        return Flux
            .fromIterable(streams)
            .parallel(10)
            .runOn(Schedulers.boundedElastic())
            .flatMap(mono ->
                mono.map(entry ->
                    Map.entry(entry.getKey(), new ByteArrayInputStream(entry.getValue().toByteArray()))
                )
            )
            .sequential();
    }

    private String extractFilenameFromHeaders(HttpHeaders headers) {
        String contentDisposition = headers.getFirst(HttpHeaders.CONTENT_DISPOSITION);

        if (contentDisposition != null && contentDisposition.contains("filename=")) {
            Pattern pattern = Pattern.compile("filename=\"?([^\"]+)\"?");
            Matcher matcher = pattern.matcher(contentDisposition);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        throw new RuntimeException("Не найдено имя файла в заголовке Content-Disposition");
    }

    private <T> Flux<T> withFilenameContext(Flux<T> flux, String fileName) {
        return flux.contextWrite(Context.of("filename", fileName));
    }
}
