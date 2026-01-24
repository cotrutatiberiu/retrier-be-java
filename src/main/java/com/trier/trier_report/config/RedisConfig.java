package com.trier.trier_report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration // tells Spring: "this class declares beans/configuration"
public class RedisConfig {

    @Bean // registers the returned RedisTemplate as a bean in the Spring context
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // Create the RedisTemplate that we'll use in services to interact with Redis
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // Tell the template how to connect to Redis (Spring Boot auto-configures this factory)
        // It uses your properties/env vars (e.g., SPRING_REDIS_HOST / SPRING_REDIS_PORT)
        template.setConnectionFactory(connectionFactory);

        // Configure how Redis KEYS are stored:
        // Redis stores bytes; this converts Java String keys <-> UTF-8 bytes
        // Example key: "user:123"
        template.setKeySerializer(new StringRedisSerializer());

        // Configure how Redis VALUES are stored:
        // This converts Java objects <-> JSON bytes (instead of Java binary serialization)
        // Example value: a UserDto becomes JSON in Redis
        // Also includes type info so it can deserialize back into the right Java class
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Return the fully configured template so Spring can inject it where needed
        return template;
    }
}

