package com.java.design.patterns.structural.decorator;

public abstract class ShapeDecorator implements Shape {

	protected Shape decoratoShape;
	
	public ShapeDecorator(Shape decoratedShape) {
		this.decoratoShape = decoratedShape;
	}

}
