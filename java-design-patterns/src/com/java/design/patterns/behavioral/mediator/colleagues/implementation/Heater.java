package com.java.design.patterns.behavioral.mediator.colleagues.implementation;

import com.java.design.patterns.behavioral.mediator.mediator.MachineMediator;
import com.java.design.patterns.behavioral.mediator.colleagues.Colleague;

public class Heater implements Colleague {
    private MachineMediator mediator;
    
    @Override
    public void setMediator(MachineMediator mediator) {
        this.mediator = mediator;
    }
    
    public void on(int temp) {
        System.out.println("Heater is on...");
        if (mediator.checkTemperature(temp)) {
            System.out.println("Temperature is set to " + temp + "°C");
            mediator.off();
        }
    }
    
    public void off() {
        System.out.println("Heater is off...");
        mediator.wash();
    }
}
