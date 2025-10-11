package com.java.design.patterns.structural.bridge.implementation.layer;

import com.java.design.patterns.structural.bridge.bridge.MusicDevice;

import java.util.ArrayList;

public class MobileMusicPlayer implements MusicDevice {

	private int volume = 5;

	private String currentTrack = "Track 1";

	private ArrayList <String> tracks = new ArrayList<>(
			) {{
				add("Track 1");
				add("Track 2");
				add("Track 3");
				add("Track 4");
				add("Track 5");
			}};

	@Override
	public void volumeUp() {
		if(volume < 10){
			volume++;
		}
		System.out.println("Mobile Video Player Volume: " + volume);
	}

	@Override
	public void volumeDown() {
		if(volume > 0){
			volume--;
		}
		System.out.println("Mobile Video Player Volume: " + volume);
	}

	@Override
	public void nextTrack() {
		currentTrack = tracks.get((tracks.indexOf(currentTrack) + 1) % tracks.size());
		System.out.println("Mobile Video Player Current Track: " + currentTrack);
	}

	@Override
	public void previousTrack() {
		currentTrack = tracks.get((tracks.indexOf(currentTrack) - 1 + tracks.size()) % tracks.size());
		System.out.println("Mobile Video Player Current Track: " + currentTrack);
	}
}
