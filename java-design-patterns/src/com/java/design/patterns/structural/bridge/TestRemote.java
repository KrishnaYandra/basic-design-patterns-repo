package com.java.design.patterns.structural.bridge;

public class TestRemote {
public static void main(String[] args) {
	RemoteButton theTV = new TVRemoteMute(new TVDevice(1, 200));
	RemoteButton theDVD = new DVDRemotePause(new DVDDevice(1, 14));
	System.out.println("Test Tv with Mute");
	theTV.buttonFivePressed();
	theTV.theDevice.buttonSevenPressed();
	theTV.buttonNinePressed();
	System.out.println("Test DVD with Pause");
	theDVD.buttonFivePressed();
	theDVD.theDevice.buttonSevenPressed();
	theDVD.buttonNinePressed();
}
}
