package com.java.design.patterns.behavioral.state.service;

public interface NotificationService {
    void sendEmail(String customerId, String subject, String body);
    void sendSMS(String customerId, String message);
}
