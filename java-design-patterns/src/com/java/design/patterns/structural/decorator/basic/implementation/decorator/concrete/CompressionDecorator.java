package com.java.design.patterns.structural.decorator.basic.implementation.decorator.concrete;

import com.java.design.patterns.structural.decorator.basic.implementation.DataProcessor;
import com.java.design.patterns.structural.decorator.basic.implementation.decorator.base.DataProcessorDecorator;

public class CompressionDecorator extends DataProcessorDecorator {
    public CompressionDecorator(DataProcessor processor) {
        super(processor);
    }
    
    @Override
    public String processData(String rawData) {
        String processed = super.processData(rawData);
        return compress(processed);
    }
    
    private String compress(String data) {
        System.out.println("Compressing data");
        return "COMPRESSED[" + data + "]";
    }
}