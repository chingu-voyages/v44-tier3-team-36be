package com.nyctransittracker.notificationsapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.mail")
public record EmailProperties(String host, String port, String username, String password) {
}
