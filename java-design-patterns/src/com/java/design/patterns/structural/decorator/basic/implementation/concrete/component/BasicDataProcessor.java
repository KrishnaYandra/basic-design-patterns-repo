package com.java.design.patterns.structural.decorator.basic.implementation.concrete.component;

import com.java.design.patterns.structural.decorator.basic.implementation.DataProcessor;

public class BasicDataProcessor implements DataProcessor {
    @Override
    public String processData(String rawData) {
        System.out.println("Basic processing of data");
        return rawData.trim();
    }
    
    @Override
    public long getProcessingTime() {
        return 10; // base processing time in ms
    }
}