package com.java.design.patterns.behavioral.strategy;

import com.java.design.patterns.behavioral.strategy.context.PaymentContext;
import com.java.design.patterns.behavioral.strategy.exception.PaymentException;
import com.java.design.patterns.behavioral.strategy.factory.PaymentFactory;
import com.java.design.patterns.behavioral.strategy.model.PaymentRequest;
import com.java.design.patterns.behavioral.strategy.model.PaymentResponse;
import com.java.design.patterns.behavioral.strategy.service.PaymentService;
import com.java.design.patterns.behavioral.strategy.strategy.PaymentStrategy;
import com.java.design.patterns.behavioral.strategy.strategy.implementation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Standalone Java application demonstrating Strategy Pattern
 * without Spring Framework - Manual Dependency Injection
 */
public class PaymentApplicationMain {
    
    public static void main(String[] args) {
        System.out.println("=== Payment Processing System - Strategy Pattern Demo ===\n");
        
        // Manual Dependency Injection - Create all strategy instances
        PaymentStrategy creditCardStrategy = new CreditCardPaymentStrategy();
        PaymentStrategy paypalStrategy = new PayPalPaymentStrategy();
        PaymentStrategy bankTransferStrategy = new BankTransferPaymentStrategy();
        
        // Create factory and inject strategies manually
        PaymentFactory paymentFactory = new PaymentFactory(
            creditCardStrategy,
            paypalStrategy,
            bankTransferStrategy
        );
        
        // Create context
        PaymentContext paymentContext = new PaymentContext();
        
        // Create service and inject dependencies manually
        PaymentService paymentService = new PaymentService(paymentContext, paymentFactory);
        
        // Run test scenarios
        runTestScenarios(paymentService);
        
        // Interactive mode
        runInteractiveMode(paymentService);
    }
    
    private static void runTestScenarios(PaymentService paymentService) {
        System.out.println("--- Running Automated Test Scenarios ---\n");
        
        // Test 1: Credit Card Payment
        System.out.println("Test 1: Processing Credit Card Payment");
        testCreditCardPayment(paymentService);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Test 2: PayPal Payment
        System.out.println("Test 2: Processing PayPal Payment");
        testPayPalPayment(paymentService);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Test 3: Bank Transfer Payment
        System.out.println("Test 3: Processing Bank Transfer Payment");
        testBankTransferPayment(paymentService);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Test 4: Invalid Payment Method
        System.out.println("Test 4: Testing Invalid Payment Method");
        testInvalidPaymentMethod(paymentService);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Test 5: Invalid Credit Card Details
        System.out.println("Test 5: Testing Invalid Credit Card Details");
        testInvalidCreditCard(paymentService);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
    }
    
    private static void testCreditCardPayment(PaymentService paymentService) {
        Map<String, String> ccDetails = new HashMap<>();
        ccDetails.put("cardNumber", "4532015112830366");
        ccDetails.put("cvv", "123");
        ccDetails.put("expiryDate", "12/26");
        
        PaymentRequest request = new PaymentRequest(
            "ORD-CC-001",
            new BigDecimal("1500.00"),
            "USD",
            "CUST-123",
            ccDetails
        );
        
        try {
            System.out.println("Order ID: " + request.getOrderId());
            System.out.println("Amount: $" + request.getAmount());
            System.out.println("Payment Method: CREDIT_CARD");
            System.out.println("Processing...");
            
            PaymentResponse response = paymentService.processPayment("CREDIT_CARD", request);
            
            System.out.println("\n✓ Payment Result:");
            System.out.println("  Transaction ID: " + response.getTransactionId());
            System.out.println("  Status: " + response.getStatus());
            System.out.println("  Message: " + response.getMessage());
            System.out.println("  Authorization Code: " + response.getAuthorizationCode());
            System.out.println("  Timestamp: " + response.getTimestamp());
            
        } catch (PaymentException e) {
            System.err.println("✗ Payment Failed:");
            System.err.println("  Error: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
        }
    }
    
    private static void testPayPalPayment(PaymentService paymentService) {
        Map<String, String> ppDetails = new HashMap<>();
        ppDetails.put("email", "customer@example.com");
        ppDetails.put("paypalToken", "PP-TOKEN-XYZ123");
        
        PaymentRequest request = new PaymentRequest(
            "ORD-PP-002",
            new BigDecimal("899.99"),
            "USD",
            "CUST-456",
            ppDetails
        );
        
        try {
            System.out.println("Order ID: " + request.getOrderId());
            System.out.println("Amount: $" + request.getAmount());
            System.out.println("Payment Method: PAYPAL");
            System.out.println("Processing...");
            
            PaymentResponse response = paymentService.processPayment("PAYPAL", request);
            
            System.out.println("\n✓ Payment Result:");
            System.out.println("  Transaction ID: " + response.getTransactionId());
            System.out.println("  Status: " + response.getStatus());
            System.out.println("  Message: " + response.getMessage());
            System.out.println("  Authorization Code: " + response.getAuthorizationCode());
            System.out.println("  Total Amount (with fees): $" + response.getAmount());
            System.out.println("  Timestamp: " + response.getTimestamp());
            
        } catch (PaymentException e) {
            System.err.println("✗ Payment Failed:");
            System.err.println("  Error: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
        }
    }
    
    private static void testBankTransferPayment(PaymentService paymentService) {
        Map<String, String> btDetails = new HashMap<>();
        btDetails.put("accountNumber", "1234567890");
        btDetails.put("routingNumber", "021000021");
        btDetails.put("bankName", "Chase Bank");
        
        PaymentRequest request = new PaymentRequest(
            "ORD-BT-003",
            new BigDecimal("5000.00"),
            "USD",
            "CUST-789",
            btDetails
        );
        
        try {
            System.out.println("Order ID: " + request.getOrderId());
            System.out.println("Amount: $" + request.getAmount());
            System.out.println("Payment Method: BANK_TRANSFER");
            System.out.println("Processing...");
            
            PaymentResponse response = paymentService.processPayment("BANK_TRANSFER", request);
            
            System.out.println("\n✓ Payment Result:");
            System.out.println("  Transaction ID: " + response.getTransactionId());
            System.out.println("  Status: " + response.getStatus());
            System.out.println("  Message: " + response.getMessage());
            System.out.println("  Reference Number: " + response.getAuthorizationCode());
            System.out.println("  Timestamp: " + response.getTimestamp());
            
        } catch (PaymentException e) {
            System.err.println("✗ Payment Failed:");
            System.err.println("  Error: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
        }
    }
    
    private static void testInvalidPaymentMethod(PaymentService paymentService) {
        Map<String, String> details = new HashMap<>();
        details.put("dummyField", "dummyValue");
        
        PaymentRequest request = new PaymentRequest(
            "ORD-INV-004",
            new BigDecimal("100.00"),
            "USD",
            "CUST-999",
            details
        );
        
        try {
            System.out.println("Order ID: " + request.getOrderId());
            System.out.println("Payment Method: BITCOIN (unsupported)");
            System.out.println("Processing...");
            
            PaymentResponse response = paymentService.processPayment("BITCOIN", request);
            System.out.println("✓ Status: " + response.getStatus());
            
        } catch (PaymentException e) {
            System.err.println("\n✗ Expected Error Caught:");
            System.err.println("  Error: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
        }
    }
    
    private static void testInvalidCreditCard(PaymentService paymentService) {
        Map<String, String> ccDetails = new HashMap<>();
        ccDetails.put("cardNumber", "1234");  // Invalid card number
        ccDetails.put("cvv", "12");  // Invalid CVV
        ccDetails.put("expiryDate", "13/20");  // Invalid expiry
        
        PaymentRequest request = new PaymentRequest(
            "ORD-INV-005",
            new BigDecimal("250.00"),
            "USD",
            "CUST-888",
            ccDetails
        );
        
        try {
            System.out.println("Order ID: " + request.getOrderId());
            System.out.println("Payment Method: CREDIT_CARD");
            System.out.println("Testing with invalid card details...");
            
            PaymentResponse response = paymentService.processPayment("CREDIT_CARD", request);
            System.out.println("✓ Status: " + response.getStatus());
            
        } catch (PaymentException e) {
            System.err.println("\n✗ Validation Error Caught:");
            System.err.println("  Error: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
        }
    }
    
    private static void runInteractiveMode(PaymentService paymentService) {
        Scanner scanner = new Scanner(System.in);
        boolean continueRunning = true;
        
        System.out.println("\n--- Interactive Payment Mode ---");
        System.out.println("Type 'exit' at any time to quit\n");
        
        while (continueRunning) {
            try {
                System.out.println("\nSelect Payment Method:");
                System.out.println("1. Credit Card");
                System.out.println("2. PayPal");
                System.out.println("3. Bank Transfer");
                System.out.println("4. Exit");
                System.out.print("Enter choice (1-4): ");
                
                String choice = scanner.nextLine().trim();
                
                if (choice.equals("4") || choice.equalsIgnoreCase("exit")) {
                    continueRunning = false;
                    System.out.println("\nThank you for using the Payment Processing System!");
                    break;
                }
                
                System.out.print("Enter Order ID: ");
                String orderId = scanner.nextLine().trim();
                
                System.out.print("Enter Amount: $");
                String amountStr = scanner.nextLine().trim();
                BigDecimal amount = new BigDecimal(amountStr);
                
                System.out.print("Enter Customer ID: ");
                String customerId = scanner.nextLine().trim();
                
                Map<String, String> paymentDetails = new HashMap<>();
                String paymentMethod = "";
                
                switch (choice) {
                    case "1":
                        paymentMethod = "CREDIT_CARD";
                        System.out.print("Enter Card Number: ");
                        paymentDetails.put("cardNumber", scanner.nextLine().trim());
                        System.out.print("Enter CVV: ");
                        paymentDetails.put("cvv", scanner.nextLine().trim());
                        System.out.print("Enter Expiry Date (MM/YY): ");
                        paymentDetails.put("expiryDate", scanner.nextLine().trim());
                        break;
                        
                    case "2":
                        paymentMethod = "PAYPAL";
                        System.out.print("Enter PayPal Email: ");
                        paymentDetails.put("email", scanner.nextLine().trim());
                        System.out.print("Enter PayPal Token: ");
                        paymentDetails.put("paypalToken", scanner.nextLine().trim());
                        break;
                        
                    case "3":
                        paymentMethod = "BANK_TRANSFER";
                        System.out.print("Enter Account Number: ");
                        paymentDetails.put("accountNumber", scanner.nextLine().trim());
                        System.out.print("Enter Routing Number: ");
                        paymentDetails.put("routingNumber", scanner.nextLine().trim());
                        System.out.print("Enter Bank Name: ");
                        paymentDetails.put("bankName", scanner.nextLine().trim());
                        break;
                        
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
                
                PaymentRequest request = new PaymentRequest(
                    orderId,
                    amount,
                    "USD",
                    customerId,
                    paymentDetails
                );
                
                System.out.println("\nProcessing payment...");
                PaymentResponse response = paymentService.processPayment(paymentMethod, request);
                
                System.out.println("\n" + "=".repeat(50));
                System.out.println("✓ PAYMENT SUCCESSFUL");
                System.out.println("=".repeat(50));
                System.out.println("Transaction ID: " + response.getTransactionId());
                System.out.println("Status: " + response.getStatus());
                System.out.println("Message: " + response.getMessage());
                System.out.println("Amount: $" + response.getAmount());
                if (response.getAuthorizationCode() != null) {
                    System.out.println("Authorization: " + response.getAuthorizationCode());
                }
                System.out.println("=".repeat(50));
                
            } catch (PaymentException e) {
                System.err.println("\n" + "=".repeat(50));
                System.err.println("✗ PAYMENT FAILED");
                System.err.println("=".repeat(50));
                System.err.println("Error: " + e.getMessage());
                System.err.println("Error Code: " + e.getErrorCode());
                System.err.println("=".repeat(50));
                
            } catch (NumberFormatException e) {
                System.err.println("Invalid amount format. Please enter a valid number.");
                
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
}
