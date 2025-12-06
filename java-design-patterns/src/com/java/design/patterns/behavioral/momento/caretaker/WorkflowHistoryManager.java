package com.java.design.patterns.behavioral.momento.caretaker;

import com.java.design.patterns.behavioral.momento.momento.WorkflowState;
import com.java.design.patterns.behavioral.momento.originator.WorkflowProcess;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class WorkflowHistoryManager {
    private Stack<WorkflowState> history;
    private Stack<WorkflowState> redoStack;
    private final int maxHistorySize;
    
    public WorkflowHistoryManager(int maxHistorySize) {
        this.history = new Stack<>();
        this.redoStack = new Stack<>();
        this.maxHistorySize = maxHistorySize;
    }
    
    public void saveState(WorkflowProcess process, String comments) {
        if (history.size() >= maxHistorySize) {
            history.remove(0); // Remove oldest state
        }
        
        WorkflowState state = process.saveState(comments);
        history.push(state);
        redoStack.clear(); // Clear redo stack when new action is performed
        
        System.out.println("State saved: " + comments + 
                          " (History size: " + history.size() + ")");
    }
    
    public boolean undo(WorkflowProcess process) {
        if (history.size() <= 1) {
            System.out.println("Cannot undo - no previous states available");
            return false;
        }
        
        // Move current state to redo stack
        WorkflowState currentState = history.pop();
        redoStack.push(currentState);
        
        // Restore previous state
        WorkflowState previousState = history.peek();
        process.restoreState(previousState);
        
        System.out.println("Undo successful - restored to previous state");
        return true;
    }
    
    public boolean redo(WorkflowProcess process) {
        if (redoStack.isEmpty()) {
            System.out.println("Cannot redo - no states to redo");
            return false;
        }
        
        WorkflowState redoState = redoStack.pop();
        history.push(redoState);
        process.restoreState(redoState);
        
        System.out.println("Redo successful");
        return true;
    }
    
    public void displayHistory() {
        System.out.println("=== Workflow History ===");
        for (int i = 0; i < history.size(); i++) {
            WorkflowState state = history.get(i);
            System.out.println((i + 1) + ". Step: " + state.getCurrentStep() + 
                              " | Status: " + state.getStatus() + 
                              " | Time: " + state.getTimestamp() + 
                              " | User: " + state.getAssignedUser());
        }
    }
    
    public List<WorkflowState> getHistory() {
        return new ArrayList<>(history);
    }
    
    public int getHistorySize() {
        return history.size();
    }
}
