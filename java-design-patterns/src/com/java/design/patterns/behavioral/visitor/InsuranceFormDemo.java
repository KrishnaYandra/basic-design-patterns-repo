package com.java.design.patterns.behavioral.visitor;

import com.java.design.patterns.behavioral.visitor.element.implementation.*;
import com.java.design.patterns.behavioral.visitor.facade.FormFacade;
import com.java.design.patterns.behavioral.visitor.service.*;
import com.java.design.patterns.behavioral.visitor.visitor.implementation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InsuranceFormDemo {
    public static void main(String[] args) {
        // Create dummy infrastructure services
        DatabaseService dbService = new DatabaseService();
        ArchivalStorageService archivalService = new ArchivalStorageService();
        KafkaProducer kafkaProducer = new KafkaProducer();

        // Wire up the operations (Visitors)
        SubmitFormOperation submitOperation = new SubmitFormOperation(dbService, kafkaProducer);
        ArchiveFormOperation archiveOperation = new ArchiveFormOperation(dbService, archivalService);

        // Create the facade for clean client API
        FormFacade formFacade = new FormFacade(submitOperation, archiveOperation);

        System.out.println("=== Insurance Form Processing Demo ===\n");

        // Demo 1: Submit an incident report
        System.out.println("1. Processing Incident Report...");
        BasicIncidentData incident = new BasicIncidentData(
            LocalDateTime.now().minusHours(2),
            "John Doe",
            "Highway 101, Mile Marker 45",
            "Rear-ended by truck while merging",
            "POL-123456789",
            "ABC123XYZ"
        );
        formFacade.submit(incident);
        System.out.println();

        // Demo 2: Submit vehicle information
        System.out.println("2. Processing Vehicle Information...");
        BasicInfoAboutCar vehicle = new BasicInfoAboutCar(
            "Sedan",
            "Toyota",
            "Camry",
            2022,
            "4T1BF1FK0HU123456",
            2500,
            "Gasoline"
        );
        formFacade.submit(vehicle);
        System.out.println();

        // Demo 3: Submit policy selection
        System.out.println("3. Processing Policy Selection...");
        PolicySelectionForm policy = new PolicySelectionForm(
            "Comprehensive",
            new BigDecimal("50000.00"),
            500,
            true,
            false,
            12
        );
        formFacade.submit(policy);
        System.out.println();

        // Demo 4: Archive old incident (simulate compliance requirement)
        System.out.println("4. Archiving Old Incident...");
        BasicIncidentData oldIncident = new BasicIncidentData(
            LocalDateTime.now().minusYears(2),
            "Jane Smith",
            "Main St, Downtown",
            "Minor fender bender",
            "POL-987654321",
            "XYZ789ABC"
        );
        formFacade.archive(oldIncident);
        System.out.println();

        // Demo 5: Archive vehicle (end of policy term)
        System.out.println("5. Archiving Vehicle Info...");
        BasicInfoAboutCar oldVehicle = new BasicInfoAboutCar(
            "SUV",
            "Ford",
            "Explorer",
            2018,
            "1FMSUABCJHL123456",
            3500,
            "Gasoline"
        );
        formFacade.archive(oldVehicle);

        System.out.println("\n=== Demo Complete ===");
        
        // Bonus: Show what was archived (for demo purposes)
        System.out.println("\nArchived records:");
        archivalService.getArchives("incidents").forEach(record ->
            System.out.println("  - " + record));
        archivalService.getArchives("vehicles").forEach(record ->
            System.out.println("  - " + record));
    }
}
