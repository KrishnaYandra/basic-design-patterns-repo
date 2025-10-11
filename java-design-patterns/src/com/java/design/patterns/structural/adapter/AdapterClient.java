package com.java.design.patterns.structural.adapter;

import com.java.design.patterns.structural.adapter.legacy.db.implementation.LegacyCustomerDB;
import com.java.design.patterns.structural.adapter.legacy.db.implementation.LegacyCustomerDBAdapter;
import com.java.design.patterns.structural.adapter.model.Customer;
import com.java.design.patterns.structural.adapter.modern.db.implementation.PostgresCustomerService;

public class AdapterClient {
    public static void main(String[] args) {
        try {
            LegacyCustomerDB legacyDb = new LegacyCustomerDB();
            CustomerService service = new LegacyCustomerDBAdapter(legacyDb, "admin", "admin@123");
            Customer customer = service.getCustomerDetails("105", "dummy-token");

            System.out.println("Customer Name: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Registered Date: " + customer.getRegisteredDate());

            System.out.println("----- After Migration to New System -----");

            // Once the db is migrated to new system, we can directly use new service implementation without changing client code
            // This is the real advantage of using adapter pattern here
            CustomerService postMigrationService = new PostgresCustomerService(null /* pass actual DB connection here */);
            Customer migratedCustomer = postMigrationService.getCustomerDetails("105", "dummy-token");
            System.out.println("Migrated Customer Name: " + migratedCustomer.getName());
            System.out.println("Migrated Email: " + migratedCustomer.getEmail());
            System.out.println("Migrated Registered Date: " + migratedCustomer.getRegisteredDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
