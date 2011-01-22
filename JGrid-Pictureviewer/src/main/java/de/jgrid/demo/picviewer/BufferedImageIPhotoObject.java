package de.jgrid.demo.picviewer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class BufferedImageIPhotoObject implements IPhotoModelObject {

	private List<BufferedImage> images;
	
	private String path;
	
	private float fraction;
	
	private int index;
	
	private boolean marker;
	
	public BufferedImageIPhotoObject(File folder) {
		this.path = folder.getName();
		images = new ArrayList<BufferedImage>();
		
		for (File pic : folder.listFiles()) {
			try {
				BufferedImage image = ImageIO.read(pic);
				if(image != null) {
					images.add(image);
				}
			} catch (Exception e) {
			}
		}
	}
	
	public BufferedImage getImage() {
		return images.get(index);
	}
	
	public void setMarker(boolean marker) {
		this.marker = marker;
	}
	
	public boolean isMarker() {
		return marker;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public Object getValueForFraction() {
		return getImage();
	}

	public String getPath() {
		return path;
	}
	
	public float getFraction() {
		return fraction;
	}
	
	@Override
	public void setFraction(float fraction) {
		this.fraction = fraction;
		this.index = (int) (fraction * (float)(images.size()));
	}

}
