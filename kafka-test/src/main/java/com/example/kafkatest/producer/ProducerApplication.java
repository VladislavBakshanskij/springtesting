package com.example.kafkatest.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.of;

@SpringBootApplication
@RequiredArgsConstructor
public class ProducerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProducerApplication.class, args);
        ProducerApplication sender = context.getBean(ProducerApplication.class);
        sender.sendTo(new HashMap<>(of("status", 1)));
    }

    private final KafkaTemplate<String, Map<String, Integer>> template;

    public void sendTo(Map<String, Integer> message) {
        template.send("super_topic_name", message);
    }
}
