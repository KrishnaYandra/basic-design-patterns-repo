package com.java.design.patterns.structural.decorator;

public class RedShapeDecorator extends ShapeDecorator {

	public RedShapeDecorator(Shape decoratedShape) {
		super(decoratedShape);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw() {
		decoratoShape.draw();
		setRedBorder(decoratoShape);
	}

	private void setRedBorder(Shape decoratoShape) {
		System.out.println("Border Color: Red");
	}

}
