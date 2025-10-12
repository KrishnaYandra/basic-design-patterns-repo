package com.java.design.patterns.structural.decorator.basic.implementation;

public interface DataProcessor {
    String processData(String rawData);
    long getProcessingTime();
}