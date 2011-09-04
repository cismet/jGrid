package de.guigarage.demos.tunes.model;

import java.io.File;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import org.tritonus.share.sampled.file.TAudioFileFormat;

public class TunesTrack {

	private String author;
	
	private String title;
	
	private String albumTitle;
	
	private long duration;
	
	private File file;
	
	private int trackNumber;
	
	public TunesTrack(File file) {
		this.file = file;
		try {
			generateInfoFromMP3Tags();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateInfoFromMP3Tags() throws Exception{
		AudioFileFormat baseFileFormat = null;
		baseFileFormat = AudioSystem.getAudioFileFormat(file);
		if (baseFileFormat instanceof TAudioFileFormat)
		{
		    @SuppressWarnings("rawtypes")
			Map properties = ((TAudioFileFormat)baseFileFormat).properties();
		    author = (String) properties.get("author");
		    albumTitle = (String) properties.get("album");
		    title = (String) properties.get("title");
		    duration = Long.parseLong(properties.get("duration").toString());
		    trackNumber = Integer.parseInt(properties.get("mp3.id3tag.track").toString().split("/")[0]);
		}
	}

	public long getDuration() {
		return duration;
	}
	
	public int getTrackNumber() {
		return trackNumber;
	}
	
	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}	
	
	public File getFile() {
		return file;
	}
}
