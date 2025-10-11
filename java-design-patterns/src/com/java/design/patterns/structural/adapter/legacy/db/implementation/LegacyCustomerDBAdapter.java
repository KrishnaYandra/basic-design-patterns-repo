package com.java.design.patterns.structural.adapter.legacy.db.implementation;

import com.java.design.patterns.structural.adapter.model.Customer;
import com.java.design.patterns.structural.adapter.CustomerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LegacyCustomerDBAdapter implements CustomerService {
    private LegacyCustomerDB legacyDb;
    private String user;
    private String pass;

    public LegacyCustomerDBAdapter(LegacyCustomerDB legacyDb, String user, String pass) {
        this.legacyDb = legacyDb;
        this.user = user;
        this.pass = pass;
    }

    @Override
    public Customer getCustomerDetails(String customerId, String authToken) throws Exception {
        // Authenticate first
        int authStatus = legacyDb.authenticateUser(user, pass);
        if (authStatus != 0) {
            throw new Exception("Authentication failed! Legacy code: " + authStatus);
        }

        // Try fetching in XML format first
        String xmlData = legacyDb.fetchCustomerXML(customerId);
        if (xmlData != null && !xmlData.isEmpty()) {
            return parseXML(xmlData, legacyDb.getFormattedDate(customerId, "IN"));
        }

        // If XML fetch fails, fallback to CSV
        String csvData = legacyDb.fetchCustomerCSV(customerId);
        if (csvData != null && !csvData.isEmpty()) {
            return parseCSV(csvData, legacyDb.getFormattedDate(customerId, "IN"));
        }

        // If CSV also fails, fallback to plain text
        String textData = legacyDb.fetchCustomerText(customerId);
        if (textData != null && !textData.isEmpty()) {
            return parsePlainText(textData, legacyDb.getFormattedDate(customerId, "IN"));
        }

        throw new Exception("Customer Not Found in any legacy data format!");
    }

    // Parses XML string to Customer object (dummy simple parser)
    private Customer parseXML(String xml, String dateStr) {
        Customer c = new Customer();
        c.setId(getTagValue(xml, "id"));
        c.setName(getTagValue(xml, "name"));
        c.setEmail(getTagValue(xml, "email"));
        c.setRegisteredDate(convertDate(dateStr));
        return c;
    }

    // Parses CSV string to Customer object
    private Customer parseCSV(String csv, String dateStr) {
        String[] parts = csv.split(",");
        Customer c = new Customer();
        if (parts.length >= 3) {
            c.setId(parts[0]);
            c.setName(parts[1]);
            c.setEmail(parts[2]);
        }
        c.setRegisteredDate(convertDate(dateStr));
        return c;
    }

    // Parses plain text string (id:name:email) to Customer object
    private Customer parsePlainText(String text, String dateStr) {
        String[] parts = text.split(":");
        Customer c = new Customer();
        if (parts.length >= 3) {
            c.setId(parts[0]);
            c.setName(parts[1]);
            c.setEmail(parts[2]);
        }
        c.setRegisteredDate(convertDate(dateStr));
        return c;
    }

    private LocalDate convertDate(String dateStr) {
        // Assuming Indian date format: dd-MM-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            // Fallback to ISO_LOCAL_DATE
            return LocalDate.parse(dateStr);
        }
    }

    // Helper to extract tag value from simple XML string
    private String getTagValue(String xml, String tag) {
        // naive XML parsing just for demo purposes
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";
        int start = xml.indexOf(openTag);
        int end = xml.indexOf(closeTag);
        if (start != -1 && end != -1 && end > start) {
            return xml.substring(start + openTag.length(), end);
        }
        return "";
    }
}
