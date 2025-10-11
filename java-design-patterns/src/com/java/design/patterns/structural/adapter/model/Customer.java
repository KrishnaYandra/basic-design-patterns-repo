package com.java.design.patterns.structural.adapter.model;

import java.time.LocalDate;

public class Customer {
    private String id;
    private String name;
    private String email;
    private LocalDate registeredDate;

    // Constructors, getters, setters
    public Customer() {}

    public Customer(String id, String name, String email, LocalDate registeredDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registeredDate = registeredDate;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getRegisteredDate() { return registeredDate; }
    public void setRegisteredDate(LocalDate registeredDate) { this.registeredDate = registeredDate; }
}
