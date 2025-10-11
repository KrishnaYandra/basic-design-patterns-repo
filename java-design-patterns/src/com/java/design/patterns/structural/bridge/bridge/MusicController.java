package com.java.design.patterns.structural.bridge.bridge;

public abstract class MusicController {

	public MusicDevice musicDevice;
	
	public MusicController(MusicDevice musicDevice) {
		this.musicDevice = musicDevice;
	}

	public void volumeUp() {
		musicDevice.volumeUp();
	}

	public void volumeDown() {
		musicDevice.volumeDown();
	}

	public void nextTrack() {
		musicDevice.nextTrack();
	}

	public void previousTrack() {
		musicDevice.previousTrack();
	}
}
