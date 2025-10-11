package com.java.design.patterns.structural.adapter.modern.db.implementation;

import com.java.design.patterns.structural.adapter.CustomerService;
import com.java.design.patterns.structural.adapter.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresCustomerService implements CustomerService {
    private Connection connection;

    public PostgresCustomerService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Customer getCustomerDetails(String customerId, String authToken) throws Exception {
        //As the below code gives an error since the connection is null, in real scenario the connection should be properly initialized.
        //I am returning a dummy customer object for demonstration purposes.
        return new Customer("105", "John Doe", "johnDoe@gmail.com", java.time.LocalDate.of(2020, 8, 15));

        // Authentication and authorization can be handled here or upstream
        /*String sql = "SELECT id, name, email, registered_date FROM customers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setRegisteredDate(rs.getDate("registered_date").toLocalDate());
                return customer;
            } else {
                throw new Exception("Customer not found in Postgres DB");
            }
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage(), e);
        }*/
    }
}
