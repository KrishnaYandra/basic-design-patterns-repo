package com.java.design.patterns.behavioral.state.service.implementation;

import com.java.design.patterns.behavioral.state.service.AuditService;

public class AuditServiceImpl implements AuditService {

    @Override
    public void logStateTransition(String orderId, String newState) {
        System.out.println("[AuditService] State change for order "
                + orderId + " -> " + newState);
    }

    @Override
    public void logStatusChange(String orderId, Enum<?> status) {
        System.out.println("[AuditService] Status change for order "
                + orderId + " -> " + status.name());
    }
}
