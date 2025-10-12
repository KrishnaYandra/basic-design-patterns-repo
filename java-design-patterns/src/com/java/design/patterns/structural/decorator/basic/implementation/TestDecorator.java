package com.java.design.patterns.structural.decorator.basic.implementation;

public class TestDecorator {
    public static void main(String[] args) {
        DataProcessingService dataProcessingService = new DataProcessingService();

        System.out.println("---- Enterprise PLAN ----");
        dataProcessingService.processMarketData("Sample Data", "ENTERPRISE");

        System.out.println("---- Basic PLAN ----");
        dataProcessingService.processMarketData("Sample Data", "BASIC");
    }
}
