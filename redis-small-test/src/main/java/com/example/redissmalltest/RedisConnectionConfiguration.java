package com.example.redissmalltest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConnectionConfiguration {
    @Bean
    public RedisConnection redisConnection(RedisConnectionFactory redisConnectionFactory) {
        return redisConnectionFactory.getConnection();
    }
}
