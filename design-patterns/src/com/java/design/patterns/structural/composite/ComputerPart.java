package com.java.design.patterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

interface Component {
	void showPrice();
}

class Leaf implements Component{
	int price;
	String name;
	public Leaf(int price, String name) {
		this.price = price;
		this.name = name;
	}
	@Override
	public void showPrice() {
		System.out.println(name + " : " + price);
	}
	
}

class Composite implements Component{
	String name;
	List<Component> components = new ArrayList<>();
	Composite(String name){
		this.name = name;
	}
	public void addComponent(Component com) {
		components.add(com);
	}
	@Override
	public void showPrice() {
		System.out.println(name);
		components.forEach(l-> l.showPrice());
	}
	
}
