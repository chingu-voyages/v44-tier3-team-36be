package com.nyctransittracker.mainapp.config;

import com.nyctransittracker.mainapp.dto.ArrivalTimeResponse;
import com.nyctransittracker.mainapp.dto.TrainPositionResponse;
import com.nyctransittracker.mainapp.dto.MtaResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

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
    public RedisTemplate<String, MtaResponse> mtaResponseRedisTemplate() {
        RedisTemplate<String, MtaResponse> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(MtaResponse.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, TrainPositionResponse> trainPositionResponseRedisTemplate() {
        RedisTemplate<String, TrainPositionResponse> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(TrainPositionResponse.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, ArrivalTimeResponse> arrivalTimeResponseRedisTemplate() {
        RedisTemplate<String, ArrivalTimeResponse> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ArrivalTimeResponse.class));
        return redisTemplate;
    }
}
