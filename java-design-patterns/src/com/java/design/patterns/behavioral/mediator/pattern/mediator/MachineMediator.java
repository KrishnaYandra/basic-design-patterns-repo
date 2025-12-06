package com.java.design.patterns.behavioral.mediator.pattern.mediator;

public interface MachineMediator {
    void start();
    void wash();
    void open();
    void closed();
    void on();
    void off();
    boolean checkTemperature(int temp);
}
