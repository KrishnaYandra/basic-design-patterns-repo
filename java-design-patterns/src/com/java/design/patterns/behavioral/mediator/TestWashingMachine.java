package com.java.design.patterns.behavioral.mediator;

import com.java.design.patterns.behavioral.mediator.colleagues.implementation.*;
import com.java.design.patterns.behavioral.mediator.helper.Sensor;
import com.java.design.patterns.behavioral.mediator.helper.SoilRemoval;
import com.java.design.patterns.behavioral.mediator.mediator.MachineMediator;
import com.java.design.patterns.behavioral.mediator.mediator.implementation.CottonMediator;
import com.java.design.patterns.behavioral.mediator.mediator.implementation.DenimMediator;
import com.java.design.patterns.behavioral.mediator.pattern.colleagues.implementation.*;
import com.java.design.patterns.behavioral.mediator.pattern.helper.*;
import com.java.design.patterns.behavioral.mediator.pattern.mediator.implementation.*;

public class TestWashingMachine {
    public static void main(String[] args) {
        // Create all colleague objects
        Sensor sensor = new Sensor();
        SoilRemoval soilRemoval = new SoilRemoval();
        Motor motor = new Motor();
        Machine machine = new Machine();
        Heater heater = new Heater();
        Valve valve = new Valve();
        Button button = new Button();
        
        // Cotton program
        MachineMediator cottonMediator = new CottonMediator(
            machine, heater, motor, sensor, soilRemoval, valve);
        
        button.setMediator(cottonMediator);
        machine.setMediator(cottonMediator);
        heater.setMediator(cottonMediator);
        valve.setMediator(cottonMediator);
        
        button.press();
        
        System.out.println("\n" + "*".repeat(80) + "\n");
        
        // Denim program
        MachineMediator denimMediator = new DenimMediator(
            machine, heater, motor, sensor, soilRemoval, valve);
        
        button.setMediator(denimMediator);
        machine.setMediator(denimMediator);
        heater.setMediator(denimMediator);
        valve.setMediator(denimMediator);
        
        button.press();
    }
}
