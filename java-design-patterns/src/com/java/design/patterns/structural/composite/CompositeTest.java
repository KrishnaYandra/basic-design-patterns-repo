package com.java.design.patterns.structural.composite;

public class CompositeTest {

	public static void main(String[] args) {
		Component hd = new Leaf(4000,"HDD");
		Component mouse = new Leaf(500, "Mouse");
		Component monitor = new Leaf(8000, "Monitory");
		Component ram = new Leaf(3000, "Ram");
		Component cpu = new Leaf(9000, "CPU");
		
		Composite ph = new Composite("Peri");
		Composite cabinent = new Composite("Cabinent");
		Composite mb = new Composite("MB");
		Composite computer = new Composite("Computer");
		
		mb.addComponent(cpu);
		mb.addComponent(ram);
		
		ph.addComponent(mouse);
		ph.addComponent(monitor);
		cabinent.addComponent(hd);
		cabinent.addComponent(mb);
		
		computer.addComponent(ph);
		computer.addComponent(cabinent);
		
		computer.showPrice();
	}

}
