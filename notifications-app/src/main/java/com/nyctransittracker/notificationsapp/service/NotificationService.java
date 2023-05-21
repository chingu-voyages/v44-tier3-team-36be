package com.nyctransittracker.notificationsapp.service;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final Session session;
    public void sendEmail() {
        Message message = new MimeMessage(session);
    }
}
