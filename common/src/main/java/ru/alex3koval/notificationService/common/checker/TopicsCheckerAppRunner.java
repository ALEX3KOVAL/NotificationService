package ru.alex3koval.notificationService.common.checker;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import ru.alex3koval.notificationService.domain.common.vo.Topic;
import ru.alex3koval.eventingImpl.factory.KafkaTopicsFetcherFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TopicsCheckerAppRunner implements ApplicationRunner {
    private final KafkaTopicsFetcherFactory factory;

    @Override
    public void run(ApplicationArguments args) {
        factory
            .create()
            .thenAccept(topicsFromBroker -> {
                    Set<String> domainRawTopics = Arrays
                        .stream(Topic.values())
                        .map(Topic::getValue)
                        .collect(Collectors.toSet());

                    boolean changed = topicsFromBroker.retainAll(domainRawTopics);

                    if (!changed) {
                        return;
                    }

                    throw new RuntimeException(
                        String.format(
                            "Топики в модуле домена и в брокере не совпадают:\nТопики в модуле домена:%s\nТопики в брокере: %s",
                            domainRawTopics,
                            topicsFromBroker
                        )
                    );
                }
            );
    }
}
