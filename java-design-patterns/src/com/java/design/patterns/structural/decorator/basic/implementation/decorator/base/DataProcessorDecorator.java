package com.java.design.patterns.structural.decorator.basic.implementation.decorator.base;

import com.java.design.patterns.structural.decorator.basic.implementation.DataProcessor;

public abstract class DataProcessorDecorator implements DataProcessor {
    protected DataProcessor processor;
    
    public DataProcessorDecorator(DataProcessor processor) {
        this.processor = processor;
    }
    
    @Override
    public String processData(String rawData) {
        return processor.processData(rawData);
    }
    
    @Override
    public long getProcessingTime() {
        return processor.getProcessingTime();
    }
}