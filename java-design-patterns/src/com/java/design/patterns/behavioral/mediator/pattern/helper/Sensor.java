package com.java.design.patterns.behavioral.mediator.pattern.helper;

public class Sensor {
    public boolean checkTemperature(int temp) {
        System.out.println("Temperature reached " + temp + " °C");
        return true;
    }
}
