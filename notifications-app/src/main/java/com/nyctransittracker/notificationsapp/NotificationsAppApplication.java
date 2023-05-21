package com.nyctransittracker.notificationsapp;

import com.nyctransittracker.notificationsapp.config.EmailConfig;
import com.nyctransittracker.notificationsapp.config.EmailProperties;
import jakarta.mail.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EmailProperties.class)
public class NotificationsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationsAppApplication.class, args);
	}

}
