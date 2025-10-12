package com.java.design.patterns.structural.decorator.basic.implementation.decorator.concrete;

import com.java.design.patterns.structural.decorator.basic.implementation.DataProcessor;
import com.java.design.patterns.structural.decorator.basic.implementation.decorator.base.DataProcessorDecorator;

public class EncryptionDecorator extends DataProcessorDecorator {
    public EncryptionDecorator(DataProcessor processor) {
        super(processor);
    }
    
    @Override
    public String processData(String rawData) {
        String processed = super.processData(rawData);
        return encrypt(processed);
    }
    
    @Override
    public long getProcessingTime() {
        return super.getProcessingTime() + 50; // encryption overhead
    }
    
    private String encrypt(String data) {
        System.out.println("Encrypting data");
        return "ENC[" + data + "]";
    }
}