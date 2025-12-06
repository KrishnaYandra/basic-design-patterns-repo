package com.java.design.patterns.behavioral.state.service;

public interface AuditService {
    void logStateTransition(String orderId, String newState);
    void logStatusChange(String orderId, Enum<?> status);
}
