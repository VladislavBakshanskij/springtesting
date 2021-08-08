package com.example.kafkatest.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

import static java.util.Map.of;

@Configuration
@EnableKafka
public class KafkaConfiguration {
    @Bean
    public ProducerFactory<String, Map<String, Integer>> producerFactory() {
        return new DefaultKafkaProducerFactory<>(of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"),
                new StringSerializer(), new JsonSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, Map<String, Integer>> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
