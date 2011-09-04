package de.guigarage.demos.tunes.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.DefaultListModel;

public class TunesAlbumListModel extends DefaultListModel implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;

	@Override
	public TunesAlbum get(int index) {
		return (TunesAlbum) super.get(index);
	}
	
	@Override
	public TunesAlbum getElementAt(int index) {
		return (TunesAlbum) super.getElementAt(index);
	}
	
	public void addByFile(File f) {
		TunesTrack track = new TunesTrack(f);
		
		TunesAlbum currentAlbum = null;
		
		for (int i = 0; i < getSize(); i++) {
			TunesAlbum album = get(i);
			if(album.getInterpreter().equals(track.getAuthor()) && album.getName().equals(track.getAlbumTitle())) {
				currentAlbum = album;
				break;
			}
		}
		if(currentAlbum == null) {
			currentAlbum = new TunesAlbum(track.getAlbumTitle(), track.getAuthor());
			addElement(currentAlbum);
		}
		currentAlbum.addTrack(track);
	}
	
	@Override
	public void add(int index, Object element) {
		if(element instanceof TunesAlbum) {
			((TunesAlbum) element).addPropertyChangeListener(this);
			super.add(index, element);	
		} else {
			throw new IllegalArgumentException("Supports only TunesAlbum!");
		}
	}
	
	@Override
	public void addElement(Object obj) {
		if(obj instanceof TunesAlbum) {
			((TunesAlbum) obj).addPropertyChangeListener(this);
			super.addElement(obj);
		} else {
			throw new IllegalArgumentException("Supports only TunesAlbum!");
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		int index = indexOf(evt.getSource());
		if(index != -1) {
			fireContentsChanged(evt.getSource(), index, index);
		}
	}
}
