package com.nyctransittracker.mainapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.data.redis")
public record RedisProperties(String username, String password, String host, int port) {
}
