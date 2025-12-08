package com.java.design.patterns.behavioral.visitor.visitor.implementation;

import com.java.design.patterns.behavioral.visitor.element.implementation.*;
import com.java.design.patterns.behavioral.visitor.service.*;
import com.java.design.patterns.behavioral.visitor.visitor.FormOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class SubmitFormOperation implements FormOperation {
    private static final Logger logger = LoggerFactory.getLogger(SubmitFormOperation.class);
    private final DatabaseService databaseService;
    private final KafkaProducer kafkaProducer;
    
    public SubmitFormOperation(DatabaseService databaseService, KafkaProducer kafkaProducer) {
        this.databaseService = databaseService;
        this.kafkaProducer = kafkaProducer;
    }
    
    @Override
    public void doOperation(BasicIncidentData incidentData) {
        logger.info("Submitting BasicIncidentData for policy: {}", 
                    incidentData.getPolicyNumber());
        
        // Validate incident data
        validateIncidentData(incidentData);
        
        // Save to incidents table
        String sql = "INSERT INTO incidents (date_time, driver_name, location, " +
                     "description, policy_number, vehicle_reg, status, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 'PENDING', NOW())";
        
        databaseService.executeUpdate(sql, 
            incidentData.getDateAndTime(),
            incidentData.getDriverName(),
            incidentData.getIncidentLocation(),
            incidentData.getIncidentDescription(),
            incidentData.getPolicyNumber(),
            incidentData.getVehicleRegistration()
        );
        
        // Trigger claim processing workflow via Kafka
        String claimEvent = buildClaimEventJson(incidentData);
        kafkaProducer.send("incident-claims-topic", claimEvent);
        
        logger.info("Incident data submitted successfully for policy: {}", 
                    incidentData.getPolicyNumber());
    }
    
    @Override
    public void doOperation(BasicInfoAboutCar carInfo) {
        logger.info("Submitting BasicInfoAboutCar: {}", carInfo.getVinNumber());
        
        // Validate car information
        validateCarInfo(carInfo);
        
        // Save to vehicles table
        String sql = "INSERT INTO vehicles (vin_number, car_type, manufacturer, model, " +
                     "year, engine_capacity, fuel_type, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        
        databaseService.executeUpdate(sql,
            carInfo.getVinNumber(),
            carInfo.getCarType(),
            carInfo.getManufacturer(),
            carInfo.getModel(),
            carInfo.getYearOfManufacture(),
            carInfo.getEngineCapacity(),
            carInfo.getFuelType()
        );
        
        // Send event to pricing service to calculate insurance rates
        String vehicleEvent = buildVehicleEventJson(carInfo);
        kafkaProducer.send("vehicle-registration-topic", vehicleEvent);
        
        logger.info("Car information submitted successfully: {}", carInfo.getVinNumber());
    }
    
    @Override
    public void doOperation(PolicySelectionForm policyForm) {
        logger.info("Submitting PolicySelectionForm: {} coverage", 
                    policyForm.getCoverageType());
        
        // Validate policy selection
        validatePolicySelection(policyForm);
        
        // Save to policies table
        String sql = "INSERT INTO policies (coverage_type, coverage_amount, deductible, " +
                     "roadside_assistance, rental_coverage, duration_months, " +
                     "status, created_at) VALUES (?, ?, ?, ?, ?, ?, 'DRAFT', NOW())";
        
        databaseService.executeUpdate(sql,
            policyForm.getCoverageType(),
            policyForm.getCoverageAmount(),
            policyForm.getDeductibleAmount(),
            policyForm.isIncludeRoadside(),
            policyForm.isIncludeRental(),
            policyForm.getPolicyDurationMonths()
        );
        
        // No Kafka event needed for policy selection - stays in DB until payment
        logger.info("Policy selection submitted successfully");
    }
    
    private void validateIncidentData(BasicIncidentData data) {
        if (data.getPolicyNumber() == null || data.getPolicyNumber().isEmpty()) {
            throw new IllegalArgumentException("Policy number is required");
        }
        if (data.getDateAndTime() == null) {
            throw new IllegalArgumentException("Incident date/time is required");
        }
    }
    
    private void validateCarInfo(BasicInfoAboutCar carInfo) {
        if (carInfo.getVinNumber() == null || carInfo.getVinNumber().length() != 17) {
            throw new IllegalArgumentException("Valid VIN number (17 chars) is required");
        }
        if (carInfo.getYearOfManufacture() < 1900) {
            throw new IllegalArgumentException("Invalid year of manufacture");
        }
    }
    
    private void validatePolicySelection(PolicySelectionForm policy) {
        if (policy.getCoverageAmount() == null || 
            policy.getCoverageAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Coverage amount must be positive");
        }
    }
    
    private String buildClaimEventJson(BasicIncidentData data) {
        return String.format("{\"eventType\":\"INCIDENT_CLAIM\"," +
                           "\"policyNumber\":\"%s\"," +
                           "\"vehicleReg\":\"%s\"," +
                           "\"incidentDate\":\"%s\"," +
                           "\"location\":\"%s\"}",
                           data.getPolicyNumber(),
                           data.getVehicleRegistration(),
                           data.getDateAndTime(),
                           data.getIncidentLocation());
    }
    
    private String buildVehicleEventJson(BasicInfoAboutCar carInfo) {
        return String.format("{\"eventType\":\"VEHICLE_REGISTERED\"," +
                           "\"vinNumber\":\"%s\"," +
                           "\"manufacturer\":\"%s\"," +
                           "\"model\":\"%s\"," +
                           "\"year\":%d}",
                           carInfo.getVinNumber(),
                           carInfo.getManufacturer(),
                           carInfo.getModel(),
                           carInfo.getYearOfManufacture());
    }
}
