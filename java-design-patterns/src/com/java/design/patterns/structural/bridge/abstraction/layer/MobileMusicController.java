package com.java.design.patterns.structural.bridge.abstraction.layer;

import com.java.design.patterns.structural.bridge.bridge.MusicController;
import com.java.design.patterns.structural.bridge.bridge.MusicDevice;

public class MobileMusicController extends MusicController {
    public MobileMusicController(MusicDevice musicDevice) {
        super(musicDevice);
    }

    public void buttonVolumeUpPressed() {
        super.volumeUp();
    }

    public void buttonVolumeDownPressed() {
        super.volumeDown();
    }

    public void buttonNextTrackPressed() {
        super.nextTrack();
    }

    public void buttonPreviousTrackPressed() {
        super.previousTrack();
    }
}
