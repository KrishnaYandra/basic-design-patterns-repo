package com.java.design.patterns.structural.bridge;

public class DVDRemotePause extends RemoteButton {

	public DVDRemotePause(EntertainmentDevice entertainmentDevice) {
		super(entertainmentDevice);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void buttonNinePressed() {
		System.out.println("TV was Paused");
	}

}
