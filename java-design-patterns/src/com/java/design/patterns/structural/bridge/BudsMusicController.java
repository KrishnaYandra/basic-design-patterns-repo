package com.java.design.patterns.structural.bridge;

public class BudsMusicController extends MusicController {

    public BudsMusicController(MusicDevice musicDevice) {
        super(musicDevice);
    }

    public void slideUp() {
        musicDevice.volumeUp();
    }

    public void slideDown() {
        musicDevice.volumeDown();
    }

    public void doubleTap() {
        musicDevice.nextTrack();
    }

    public void TripleTap() {
        musicDevice.previousTrack();
    }
}
