package com.java.design.patterns.behavioral.momento.momento;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

public class WorkflowState {
    private final String currentStep;
    private final String status;
    private final Map<String, Object> processData;
    private final String assignedUser;
    private final LocalDateTime timestamp;
    private final String comments;
    
    public WorkflowState(String currentStep, String status, 
                        Map<String, Object> processData, String assignedUser, 
                        String comments) {
        this.currentStep = currentStep;
        this.status = status;
        this.processData = new HashMap<>(processData);
        this.assignedUser = assignedUser;
        this.comments = comments;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getCurrentStep() { return currentStep; }
    public String getStatus() { return status; }
    public Map<String, Object> getProcessData() { return new HashMap<>(processData); }
    public String getAssignedUser() { return assignedUser; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getComments() { return comments; }
}
