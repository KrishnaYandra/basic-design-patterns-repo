package com.java.design.patterns.structural.bridge;

public class TestMusicController {
public static void main(String[] args) {
	//This below code shows that switching of implementation is seamless
	//We can switch the implementation at runtime
	//We can also add more implementation of MusicDevice and use it with existing MusicController without changing the MusicController code
	//We can also add more implementation of MusicController and use it with existing MusicDevice without changing the MusicDevice code
	//This is the main advantage of Bridge Design Pattern

	MusicDevice mobile = new MobileMusicPlayer();
	MobileMusicController mobileMusicController = new MobileMusicController(mobile);
	mobileMusicController.buttonVolumeUpPressed();
	mobileMusicController.buttonNextTrackPressed();
	mobileMusicController.buttonVolumeDownPressed();
	mobileMusicController.buttonPreviousTrackPressed();

	BudsMusicController budsMusicController = new BudsMusicController(mobile);
	budsMusicController.slideUp();
	budsMusicController.doubleTap();
	budsMusicController.slideDown();
	budsMusicController.TripleTap();
}
}
