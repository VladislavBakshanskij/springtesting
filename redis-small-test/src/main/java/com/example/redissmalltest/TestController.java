package com.example.redissmalltest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final RedisConnection connection;

    @GetMapping
    public String get(@RequestParam String key) {
        final var bytes = connection.get(key.getBytes());
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }

    @PostMapping
    public String set(@RequestParam String key, @RequestParam String value) {
        final var set = connection.set(key.getBytes(), value.getBytes(),
                Expiration.from(5, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT);
        if (set != null && set) {
            return "success";
        }
        return "failed";
    }

    @PreDestroy
    public void destroy() {
        log.info("Destroy method is call");
        connection.close();
    }
}
