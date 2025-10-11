package com.java.design.patterns.structural.bridge;

public abstract class RemoteButton {

	public EntertainmentDevice theDevice;
	
	public RemoteButton(EntertainmentDevice entertainmentDevice) {
		theDevice = entertainmentDevice;
	}
	
	public void buttonFivePressed() {
		theDevice.buttonFivePressed();
	}
	
	public void buttonSixPressed() {
		theDevice.buttonSixPressed();
	}
	
	public void deviceFeedback() {
		theDevice.deviceFeedback();
	}
	
	public abstract void buttonNinePressed();
}
