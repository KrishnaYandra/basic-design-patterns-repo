package com.java.design.patterns.behavioral.chain.model;

public class ValidationError {
    private String field;
    private String message;
    private ErrorSeverity severity;
    
    public ValidationError(String field, String message, ErrorSeverity severity) {
        this.field = field;
        this.message = message;
        this.severity = severity;
    }
    
    // Getters
    public String getField() { return field; }
    public String getMessage() { return message; }
    public ErrorSeverity getSeverity() { return severity; }
}