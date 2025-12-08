package com.java.design.patterns.behavioral.visitor.element.implementation;

import com.java.design.patterns.behavioral.visitor.element.Form;
import com.java.design.patterns.behavioral.visitor.visitor.FormOperation;

import java.time.LocalDateTime;

public class BasicIncidentData implements Form {
    private LocalDateTime dateAndTime;
    private String driverName;
    private String incidentLocation;
    private String incidentDescription;
    private String policyNumber;
    private String vehicleRegistration;
    
    public BasicIncidentData(LocalDateTime dateAndTime, String driverName, 
                             String incidentLocation, String incidentDescription,
                             String policyNumber, String vehicleRegistration) {
        this.dateAndTime = dateAndTime;
        this.driverName = driverName;
        this.incidentLocation = incidentLocation;
        this.incidentDescription = incidentDescription;
        this.policyNumber = policyNumber;
        this.vehicleRegistration = vehicleRegistration;
    }
    
    @Override
    public void doOperation(FormOperation formOperation) {
        formOperation.doOperation(this);  // Double dispatch
    }
    
    // Getters
    public LocalDateTime getDateAndTime() { return dateAndTime; }
    public String getDriverName() { return driverName; }
    public String getIncidentLocation() { return incidentLocation; }
    public String getIncidentDescription() { return incidentDescription; }
    public String getPolicyNumber() { return policyNumber; }
    public String getVehicleRegistration() { return vehicleRegistration; }
}
