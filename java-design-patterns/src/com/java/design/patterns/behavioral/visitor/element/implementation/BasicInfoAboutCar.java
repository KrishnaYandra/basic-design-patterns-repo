package com.java.design.patterns.behavioral.visitor.element.implementation;

import com.java.design.patterns.behavioral.visitor.element.Form;
import com.java.design.patterns.behavioral.visitor.visitor.FormOperation;

public class BasicInfoAboutCar implements Form {
    private String carType;
    private String manufacturer;
    private String model;
    private int yearOfManufacture;
    private String vinNumber;
    private int engineCapacity;
    private String fuelType;
    
    public BasicInfoAboutCar(String carType, String manufacturer, String model,
                             int yearOfManufacture, String vinNumber, 
                             int engineCapacity, String fuelType) {
        this.carType = carType;
        this.manufacturer = manufacturer;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
        this.vinNumber = vinNumber;
        this.engineCapacity = engineCapacity;
        this.fuelType = fuelType;
    }
    
    @Override
    public void doOperation(FormOperation formOperation) {
        formOperation.doOperation(this);  // Double dispatch
    }
    
    // Getters
    public String getCarType() { return carType; }
    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }
    public int getYearOfManufacture() { return yearOfManufacture; }
    public String getVinNumber() { return vinNumber; }
    public int getEngineCapacity() { return engineCapacity; }
    public String getFuelType() { return fuelType; }
}
