package com.java.design.patterns.behavioral.visitor.visitor.implementation;

import com.java.design.patterns.behavioral.visitor.element.implementation.*;
import com.java.design.patterns.behavioral.visitor.service.*;
import com.java.design.patterns.behavioral.visitor.visitor.FormOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;

public class ArchiveFormOperation implements FormOperation {
    private static final Logger logger = LoggerFactory.getLogger(ArchiveFormOperation.class);
    private final DatabaseService databaseService;
    private final ArchivalStorageService archivalStorage;
    
    public ArchiveFormOperation(DatabaseService databaseService, 
                                ArchivalStorageService archivalStorage) {
        this.databaseService = databaseService;
        this.archivalStorage = archivalStorage;
    }
    
    @Override
    public void doOperation(BasicIncidentData incidentData) {
        logger.info("Archiving incident for policy: {}", incidentData.getPolicyNumber());
        
        // Move to cold storage and anonymize sensitive data
        String archiveRecord = buildArchiveRecord(incidentData);
        archivalStorage.store("incidents", archiveRecord);
        
        // Update status in main database
        databaseService.executeUpdate(
            "UPDATE incidents SET status = 'ARCHIVED', archived_at = ? " +
            "WHERE policy_number = ? AND date_time = ?",
            LocalDateTime.now(),
            incidentData.getPolicyNumber(),
            incidentData.getDateAndTime()
        );
        
        logger.info("Incident archived successfully");
    }
    
    @Override
    public void doOperation(BasicInfoAboutCar carInfo) {
        logger.info("Archiving vehicle info: {}", carInfo.getVinNumber());
        
        // Move to archival storage with reduced detail
        String archiveRecord = buildArchiveRecord(carInfo);
        archivalStorage.store("vehicles", archiveRecord);
        
        // Mark as archived in database
        databaseService.executeUpdate(
            "UPDATE vehicles SET archived = true, archived_at = ? WHERE vin_number = ?",
            LocalDateTime.now(),
            carInfo.getVinNumber()
        );
        
        logger.info("Vehicle information archived successfully");
    }
    
    @Override
    public void doOperation(PolicySelectionForm policyForm) {
        logger.info("Archiving policy: {}", policyForm.getCoverageType());
        
        // Archive expired or cancelled policies
        String archiveRecord = buildArchiveRecord(policyForm);
        archivalStorage.store("policies", archiveRecord);
        
        logger.info("Policy archived successfully");
    }
    
    private String buildArchiveRecord(BasicIncidentData data) {
        // Anonymize driver name, keep only essential data
        return String.format("INCIDENT|%s|REDACTED|%s|%s",
                           data.getPolicyNumber(),
                           data.getIncidentLocation(),
                           data.getDateAndTime());
    }
    
    private String buildArchiveRecord(BasicInfoAboutCar carInfo) {
        return String.format("VEHICLE|%s|%s|%s|%d",
                           carInfo.getVinNumber(),
                           carInfo.getManufacturer(),
                           carInfo.getModel(),
                           carInfo.getYearOfManufacture());
    }
    
    private String buildArchiveRecord(PolicySelectionForm policy) {
        return String.format("POLICY|%s|%s|%d",
                           policy.getCoverageType(),
                           policy.getCoverageAmount(),
                           policy.getPolicyDurationMonths());
    }
}
