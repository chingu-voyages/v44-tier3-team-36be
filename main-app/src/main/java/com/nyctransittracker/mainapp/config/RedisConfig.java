package com.nyctransittracker.mainapp.config;

import com.nyctransittracker.mainapp.model.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redisProperties;
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setUsername(redisProperties.username());
        config.setPassword(redisProperties.password());
        config.setHostName(redisProperties.host());
        config.setPort(redisProperties.port());
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, DataResponse> redisTemplate() {
        RedisTemplate<String, DataResponse> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(DataResponse.class));
        return redisTemplate;
    }
}
