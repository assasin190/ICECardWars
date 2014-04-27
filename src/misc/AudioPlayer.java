package misc;

import javax.sound.sampled.*;

public class AudioPlayer {

	private Clip clip;

	public AudioPlayer(String s) {

		try {

			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass()
					.getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void play() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}

	public void playLoop() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}

	public boolean isPlay() {
		if (clip.isRunning())
			return true;
		else
			return false;
	}

	public void close() {
		stop();
		clip.close();
	}

}
