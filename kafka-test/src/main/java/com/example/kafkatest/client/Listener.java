package com.example.kafkatest.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Map;

@Slf4j
public class Listener {
    private final TaskExecutor asyncExecutor = new SimpleAsyncTaskExecutor();

    @KafkaListener(topics = "super_topic_name")
    public void listen(Map<String, Object> data) {
        asyncExecutor.execute(() -> log.info("data from producer {}", data));
    }
}
