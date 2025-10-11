package com.java.design.patterns.structural.bridge.abstraction.layer;

import com.java.design.patterns.structural.bridge.bridge.MusicController;
import com.java.design.patterns.structural.bridge.bridge.MusicDevice;

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
