package com.java.design.patterns.structural.decorator.basic.implementation.decorator.concrete;

import com.java.design.patterns.structural.decorator.basic.implementation.DataProcessor;
import com.java.design.patterns.structural.decorator.basic.implementation.decorator.base.DataProcessorDecorator;

public class ValidationDecorator extends DataProcessorDecorator {
    public ValidationDecorator(DataProcessor processor) {
        super(processor);
    }
    
    @Override
    public String processData(String rawData) {
        validateData(rawData);
        return super.processData(rawData);
    }
    
    @Override
    public long getProcessingTime() {
        return super.getProcessingTime() + 25;
    }
    
    private void validateData(String data) {
        System.out.println("Validating data");
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }
    }
}