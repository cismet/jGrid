package de.guigarage.demos.tunes.player;

import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import de.guigarage.demos.tunes.model.TunesTrack;

public class TunesPlayer {

	private SourceDataLine line;

	private AudioInputStream audioInputStream;

	private static TunesPlayer instance = new TunesPlayer();

	private boolean play;

	private TunesTrack track;

	private TunesPlayer() {
	}

	public static TunesPlayer getInstance() {
		return instance;
	}

	public void play() throws Exception {
		if (line != null) {
			line.start();
			play = true;
			byte[] data = new byte[4096];
			int nBytesRead = 0;
			while (nBytesRead != -1 && play) {
				nBytesRead = audioInputStream.read(data, 0, data.length);
				if (nBytesRead != -1)
					line.write(data, 0, nBytesRead);
			}
		}
	}

	public void pause() {
		if (line != null) {
			play = false;
			line.stop();
		}
	}

	public long getMicrosecondPosition() {
		if (line != null) {
			return line.getMicrosecondPosition();
		}
		return -1;
	}

	public TunesTrack getTrack() {
		return track;
	}

	public void stop() {
		if (line != null) {
			pause();
			line = null;
			track = null;
		}
	}

	public boolean hasTrack() {
		return line != null;
	}

	private void resetFrame() throws Exception {
		InputStream fileInput = track.getFile().toURI().toURL()
				.openConnection().getInputStream();

		AudioInputStream in = AudioSystem.getAudioInputStream(fileInput);
		AudioFormat baseFormat = in.getFormat();
		AudioFormat decodedFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(),
				16, baseFormat.getChannels(), baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(), false);
		audioInputStream = AudioSystem.getAudioInputStream(decodedFormat, in);

		line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				decodedFormat);
		line = (SourceDataLine) AudioSystem.getLine(info);
		line.open(decodedFormat);
	}

	public synchronized void open(TunesTrack track) throws Exception {
		this.track = track;
		resetFrame();
	}

}
