package com.java.design.patterns.structural.adapter;

import com.java.design.patterns.structural.adapter.model.Customer;

public interface CustomerService {
    Customer getCustomerDetails(String customerId, String authToken) throws Exception;
}