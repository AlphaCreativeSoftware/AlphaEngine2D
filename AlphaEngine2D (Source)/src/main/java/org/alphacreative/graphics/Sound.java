package org.alphacreative.graphics;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	private Clip clip;
	private FloatControl volume;
	
	public Sound(Clip clip) {
		this.clip = clip;
		volume = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	public void Play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void Loop() {
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void Stop() {
		clip.stop();
	}
	
	public int GetFramePosition() {
		return clip.getFramePosition();
	}
	
	public void ChangeVolume(float value) {
		volume.setValue(value);
	}

	public void SetClip(Clip clip)
	{
		this.clip = clip;
	}
	
}