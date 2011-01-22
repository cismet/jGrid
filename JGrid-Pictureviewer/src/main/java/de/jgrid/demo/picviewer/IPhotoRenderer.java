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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import de.loganmobile.grid.GridCellRenderer;
import de.loganmobile.grid.JGrid;

public class IPhotoRenderer extends JComponent implements GridCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private String tooltip;
	
	private boolean paintMarker = false;
	
	private float markerFraction;
	
	@Override
	public Component getGridCellRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		image = null;
		tooltip = "unknown";
		paintMarker = false;
		if(value instanceof BufferedImageIPhotoObject) {
			image = ((BufferedImageIPhotoObject) value).getImage();
			tooltip = ((BufferedImageIPhotoObject) value).getPath() + "(" + ((BufferedImageIPhotoObject) value).getIndex() + ")";
			markerFraction = ((BufferedImageIPhotoObject) value).getFraction();
			paintMarker = ((BufferedImageIPhotoObject) value).isMarker();
		}
		return this;
	}

	@Override
	public String getToolTipText() {
		return tooltip;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image != null) {
			int width = image.getWidth();
			int height = image.getHeight();
			
//			float widthFactor = (float)getWidth() / (float)image.getWidth();
//			float heightFactor = (float)getHeight() / (float)image.getHeight();
//			if(widthFactor > heightFactor) {
//				width = (int) ((float)image.getWidth() * widthFactor);
//				height = (int) ((float)image.getHeight() * widthFactor);
//			} else {
//				width = (int) ((float)image.getWidth() * heightFactor);
//				height = (int) ((float)image.getHeight() * heightFactor);
//			}
//			image = ImageUtilities.getOptimalScalingImage(image, width, height);
//			g.drawImage(image, 0, 0, null);
			
			while(width < getWidth()) {
				width = width * 2;
				height = height * 2;
			}
			while(height < getHeight()) {
				width = width * 2;
				height = height * 2;
			}
			g.drawImage(image, 0, 0, width, height, null);
			
			if(paintMarker) {
				int x = (int) (getWidth() * markerFraction);
				
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				g2.setStroke(new BasicStroke(3.5f));
				g2.setColor(new Color(50, 50, 50));
				g2.drawLine(x, 0, x, getHeight());
				
				g2.setStroke(new BasicStroke(2.5f));
				g2.setColor(new Color(248,211,80));
				g2.drawLine(x, 0, x, getHeight());
				
				g2.dispose();
			}
		}
	}

	
	
	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 * 
	 * @since 1.5
	 * @return <code>true</code> if the background is completely opaque and
	 *         differs from the JList's background; <code>false</code> otherwise
	 */
	@Override
	public boolean isOpaque() {
		Color back = getBackground();
		Component p = getParent();
		if (p != null) {
			p = p.getParent();
		}
		// p should now be the JList.
		boolean colorMatch = (back != null) && (p != null)
				&& back.equals(p.getBackground()) && p.isOpaque();
		return !colorMatch && super.isOpaque();
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void validate() {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 * 
	 * @since 1.5
	 */
	@Override
	public void invalidate() {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 * 
	 * @since 1.5
	 */
	@Override
	public void repaint() {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void revalidate() {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void repaint(long tm, int x, int y, int width, int height) {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void repaint(Rectangle r) {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		// Strings get interned...
		if (propertyName == "text"
				|| ((propertyName == "font" || propertyName == "foreground")
						&& oldValue != newValue && getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null)) {

			super.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void firePropertyChange(String propertyName, byte oldValue,
			byte newValue) {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void firePropertyChange(String propertyName, char oldValue,
			char newValue) {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void firePropertyChange(String propertyName, short oldValue,
			short newValue) {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void firePropertyChange(String propertyName, int oldValue,
			int newValue) {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void firePropertyChange(String propertyName, long oldValue,
			long newValue) {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void firePropertyChange(String propertyName, float oldValue,
			float newValue) {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void firePropertyChange(String propertyName, double oldValue,
			double newValue) {
	}

	/**
	 * Overridden for performance reasons. See the <a
	 * href="#override">Implementation Note</a> for more information.
	 */
	@Override
	public void firePropertyChange(String propertyName, boolean oldValue,
			boolean newValue) {
	}
}
