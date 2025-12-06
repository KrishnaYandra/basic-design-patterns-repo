package com.java.design.patterns.behavioral.state.service.implementation;

import com.java.design.patterns.behavioral.state.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendEmail(String customerId, String subject, String body) {
        System.out.println("[NotificationService] EMAIL to " + customerId
                + " | Subject: " + subject + " | Body: " + body);
    }

    @Override
    public void sendSMS(String customerId, String message) {
        System.out.println("[NotificationService] SMS to " + customerId
                + " | Message: " + message);
    }
}
