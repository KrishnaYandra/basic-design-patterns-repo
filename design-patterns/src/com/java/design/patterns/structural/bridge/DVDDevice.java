package com.java.design.patterns.structural.bridge;

public class DVDDevice extends EntertainmentDevice{

	public DVDDevice(int newDeviceState, int newMaxSetting) {
		deviceState = newDeviceState;
		maxSetting = newMaxSetting;
	}
	
	@Override
	public void buttonFivePressed() {
		System.out.println("Chapter Down");
		deviceState--;
	}

	@Override
	public void buttonSixPressed() {
		System.out.println("Chapter Up");
		deviceState++;
	}

	
}
