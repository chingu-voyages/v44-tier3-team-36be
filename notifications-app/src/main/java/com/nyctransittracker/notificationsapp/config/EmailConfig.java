package com.nyctransittracker.notificationsapp.config;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailConfig implements CommandLineRunner {

    private final EmailProperties emailProperties;

    @Bean
    Properties properties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", emailProperties.host());
        props.put("mail.smtp.port", emailProperties.port());
        return props;
    }
    @Bean
    Session session(Properties properties) {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProperties.username(), emailProperties.password());
            }
        };
        return Session.getInstance(properties, authenticator);
    }

    @Override
    public void run(String... args) throws Exception {
        Properties properties = properties();
        Session session = session(properties);
        System.out.println("testing");
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailProperties.username()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("shawnkoong@gmail.com"));
        message.setSubject("Chingu Test");
        message.setText("testing sending out email");
        Transport.send(message);
    }
}
