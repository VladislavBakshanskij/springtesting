package com.example.kafkatest.client;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

import static java.util.Map.of;

@Configuration
@EnableKafka
public class KafkaConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Map<String, Object>> kafkaListenerContainerFactory(
            ConsumerFactory<String, Map<String, Object>> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Map<String, Object>> kafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        kafkaListenerContainerFactory.setStatefulRetry(true);
        return kafkaListenerContainerFactory;
    }

    @Bean
    public ConsumerFactory<String, Map<String, Object>> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(buildProps(), new StringDeserializer(), new JsonDeserializer<>());
    }

    @Bean
    public Listener listener() {
        return new Listener();
    }

    private Map<String, Object> buildProps() {
        return of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.GROUP_ID_CONFIG, "group");
    }
}
