package com.java.design.patterns.structural.adapter.legacy.db.implementation;

public class LegacyCustomerDB {
    public String fetchCustomerText(String id) {
        // returns plain text customer data, e.g. "id:name:email"
        if ("105".equals(id)) return "105:John Doe:john@example.com";
        return null;
    }
    
    public String fetchCustomerXML(String id) {
        // returns customer data in XML format
        if ("105".equals(id)) return "<customer><id>105</id><name>John Doe</name><email>john@example.com</email></customer>";
        return null;
    }
    
    public String fetchCustomerCSV(String id) {
        // returns customer data in CSV format: id,name,email
        if ("105".equals(id)) return "105,John Doe,john@example.com";
        return null;
    }
    
    public int authenticateUser(String user, String pass) {
        // returns 0 if success, otherwise error code
        if ("admin".equals(user) && "admin@123".equals(pass)) return 0;
        return 1; // auth failed
    }
    
    public String getFormattedDate(String id, String locale) {
        // simulate locale-based date format
        if ("IN".equals(locale)) return "15-08-2020";
        else if ("US".equals(locale)) return "08/15/2020";
        return "2020-08-15";
    }
}
