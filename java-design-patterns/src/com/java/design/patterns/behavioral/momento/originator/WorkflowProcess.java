package com.java.design.patterns.behavioral.momento.originator;

import com.java.design.patterns.behavioral.momento.momento.WorkflowState;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class WorkflowProcess {
    private String processId;
    private String currentStep;
    private String status;
    private Map<String, Object> processData;
    private String assignedUser;
    private List<String> availableActions;
    
    public WorkflowProcess(String processId) {
        this.processId = processId;
        this.currentStep = "INITIATED";
        this.status = "PENDING";
        this.processData = new HashMap<>();
        this.availableActions = new ArrayList<>();
        initializeAvailableActions();
    }
    
    private void initializeAvailableActions() {
        availableActions.add("APPROVE");
        availableActions.add("REJECT");
        availableActions.add("REQUEST_INFO");
    }
    
    public void executeAction(String action, String user, String comments, 
                            Map<String, Object> additionalData) {
        if (!availableActions.contains(action)) {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
        
        this.assignedUser = user;
        this.processData.putAll(additionalData);
        
        switch (action) {
            case "APPROVE":
                this.currentStep = getNextApprovalStep();
                this.status = isLastStep() ? "COMPLETED" : "PENDING";
                break;
            case "REJECT":
                this.currentStep = "REJECTED";
                this.status = "REJECTED";
                break;
            case "REQUEST_INFO":
                this.currentStep = "INFO_REQUESTED";
                this.status = "WAITING_FOR_INFO";
                break;
        }
        
        updateAvailableActions();
        System.out.println("Action '" + action + "' executed by " + user + 
                          ". Current step: " + currentStep);
    }
    
    private String getNextApprovalStep() {
        switch (currentStep) {
            case "INITIATED": return "LEVEL1_APPROVAL";
            case "LEVEL1_APPROVAL": return "LEVEL2_APPROVAL";
            case "LEVEL2_APPROVAL": return "FINAL_APPROVAL";
            default: return "COMPLETED";
        }
    }
    
    private boolean isLastStep() {
        return "FINAL_APPROVAL".equals(currentStep);
    }
    
    private void updateAvailableActions() {
        availableActions.clear();
        if ("COMPLETED".equals(status) || "REJECTED".equals(status)) {
            return;
        }
        availableActions.add("APPROVE");
        availableActions.add("REJECT");
        if (!"INFO_REQUESTED".equals(currentStep)) {
            availableActions.add("REQUEST_INFO");
        }
    }
    
    public WorkflowState saveState(String comments) {
        return new WorkflowState(currentStep, status, processData, 
                                assignedUser, comments);
    }
    
    public void restoreState(WorkflowState state) {
        this.currentStep = state.getCurrentStep();
        this.status = state.getStatus();
        this.processData = state.getProcessData();
        this.assignedUser = state.getAssignedUser();
        updateAvailableActions();
        System.out.println("Workflow restored to step: " + currentStep + 
                          " at " + state.getTimestamp());
    }
    
    public void displayCurrentState() {
        System.out.println("=== Workflow Status ===");
        System.out.println("Process ID: " + processId);
        System.out.println("Current Step: " + currentStep);
        System.out.println("Status: " + status);
        System.out.println("Assigned User: " + assignedUser);
        System.out.println("Available Actions: " + availableActions);
        System.out.println("Process Data: " + processData);
    }
}
