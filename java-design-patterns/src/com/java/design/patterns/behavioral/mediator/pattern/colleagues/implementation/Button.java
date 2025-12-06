package com.java.design.patterns.behavioral.mediator.pattern.colleagues.implementation;

import com.java.design.patterns.behavioral.mediator.pattern.mediator.MachineMediator;
import com.java.design.patterns.behavioral.mediator.pattern.colleagues.Colleague;

public class Button implements Colleague {
    private MachineMediator mediator;
    
    @Override
    public void setMediator(MachineMediator mediator) {
        this.mediator = mediator;
    }
    
    public void press() {
        System.out.println("Button pressed.");
        mediator.start();
    }
}
