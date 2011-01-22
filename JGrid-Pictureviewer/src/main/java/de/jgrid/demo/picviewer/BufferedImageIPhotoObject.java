/*
 * Created on Jan 22, 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2011 Hendrik Ebbers
 */
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
