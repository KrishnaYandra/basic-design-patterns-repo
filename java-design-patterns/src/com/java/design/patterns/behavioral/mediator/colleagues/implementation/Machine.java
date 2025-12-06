package com.java.design.patterns.behavioral.mediator.colleagues.implementation;

import com.java.design.patterns.behavioral.mediator.mediator.MachineMediator;
import com.java.design.patterns.behavioral.mediator.colleagues.Colleague;

public class Machine implements Colleague {
    private MachineMediator mediator;
    
    @Override
    public void setMediator(MachineMediator mediator) {
        this.mediator = mediator;
    }
    
    public void start() {
        mediator.open();
    }
    
    public void wash() {
        mediator.wash();
    }
}
