package com.java.design.patterns.structural.bridge;

public class TVRemoteMute extends RemoteButton {

	public TVRemoteMute(EntertainmentDevice entertainmentDevice) {
		super(entertainmentDevice);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void buttonNinePressed() {
		System.out.println("TV was Muted");
	}

}
