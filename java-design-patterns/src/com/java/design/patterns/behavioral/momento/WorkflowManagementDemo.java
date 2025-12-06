package com.java.design.patterns.behavioral.momento;

import com.java.design.patterns.behavioral.momento.caretaker.WorkflowHistoryManager;
import com.java.design.patterns.behavioral.momento.originator.WorkflowProcess;

import java.util.HashMap;
import java.util.Map;

public class WorkflowManagementDemo {
    public static void main(String[] args) {
        // Initialize workflow system
        WorkflowProcess process = new WorkflowProcess("WF-2024-001");
        WorkflowHistoryManager historyManager = new WorkflowHistoryManager(10);
        
        // Save initial state
        historyManager.saveState(process, "Workflow initiated");
        process.displayCurrentState();
        
        System.out.println("\n" + "=".repeat(50));
        
        // Execute first approval
        Map<String, Object> approvalData = new HashMap<>();
        approvalData.put("reviewComments", "Initial review completed");
        approvalData.put("priority", "HIGH");
        
        process.executeAction("APPROVE", "john.smith", 
                            "Level 1 approval completed", approvalData);
        historyManager.saveState(process, "Level 1 approved by john.smith");
        process.displayCurrentState();
        
        System.out.println("\n" + "=".repeat(50));
        
        // Execute second approval
        approvalData.put("budgetApproved", true);
        approvalData.put("estimatedCost", 15000);
        
        process.executeAction("APPROVE", "jane.doe", 
                            "Level 2 approval with budget", approvalData);
        historyManager.saveState(process, "Level 2 approved by jane.doe");
        process.displayCurrentState();
        
        System.out.println("\n" + "=".repeat(50));
        
        // Request additional information
        Map<String, Object> infoRequest = new HashMap<>();
        infoRequest.put("infoRequested", "Need technical specifications");
        infoRequest.put("dueDate", "2024-08-30");
        
        process.executeAction("REQUEST_INFO", "bob.wilson", 
                            "Additional info required", infoRequest);
        historyManager.saveState(process, "Info requested by bob.wilson");
        process.displayCurrentState();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DEMONSTRATING UNDO/REDO FUNCTIONALITY");
        System.out.println("=".repeat(50));
        
        // Display history
        historyManager.displayHistory();
        
        // Undo last action
        System.out.println("\nPerforming UNDO...");
        historyManager.undo(process);
        process.displayCurrentState();
        
        // Undo again
        System.out.println("\nPerforming another UNDO...");
        historyManager.undo(process);
        process.displayCurrentState();
        
        // Redo
        System.out.println("\nPerforming REDO...");
        historyManager.redo(process);
        process.displayCurrentState();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("FINAL HISTORY STATE");
        historyManager.displayHistory();
    }
}
