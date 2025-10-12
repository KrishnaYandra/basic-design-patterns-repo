package com.java.design.patterns.structural.decorator.basic.implementation;

import com.java.design.patterns.structural.decorator.basic.implementation.decorator.concrete.CompressionDecorator;
import com.java.design.patterns.structural.decorator.basic.implementation.decorator.concrete.EncryptionDecorator;
import com.java.design.patterns.structural.decorator.basic.implementation.decorator.concrete.ValidationDecorator;
import com.java.design.patterns.structural.decorator.basic.implementation.concrete.component.BasicDataProcessor;

// Usage - Dynamic pipeline configuration
public class DataProcessingService {
    public void processMarketData(String rawData, String clientType) {
        DataProcessor processor = new BasicDataProcessor();
        
        // Different processing chains based on client requirements
        if ("ENTERPRISE".equals(clientType)) {
            processor = new ValidationDecorator(processor);
            processor = new EncryptionDecorator(processor);
            processor = new CompressionDecorator(processor);
        } else if ("BASIC".equals(clientType)) {
            processor = new ValidationDecorator(processor);
        }
        
        String result = processor.processData(rawData);
        System.out.println("Processed: " + result);
        System.out.println("Total processing time: " + processor.getProcessingTime() + "ms");
    }
}