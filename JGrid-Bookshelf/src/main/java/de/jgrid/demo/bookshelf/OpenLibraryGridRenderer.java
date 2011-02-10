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
package de.jgrid.demo.bookshelf;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import de.jgrid.GridCellRenderer;
import de.jgrid.JGrid;
import de.jgrid.demo.util.ImageUtilities;

public class OpenLibraryGridRenderer extends JComponent implements
		GridCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Book book;
	
	private boolean isSelected;
	
	@Override
	public Component getGridCellRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		this.book = null;
		this.isSelected = isSelected;
		if(value instanceof Book) {
			this.book = (Book) value;
		}
		
		return this;
	}
	
	@Override
	public String getToolTipText() {
		if(book != null) {
			return book.getTitel();
		}
		return super.getToolTipText();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(book != null && book.getCover() != null) {
			int width = book.getCover().getWidth();
			int height = book.getCover().getHeight() - 10;
			
			float widthFactor = (float)getWidth() / (float)book.getCover().getWidth();
			float heightFactor = (float)(getHeight() - 10) / (float)book.getCover().getHeight();
			if(widthFactor < heightFactor) {
				width = (int) ((float)book.getCover().getWidth() * widthFactor);
				height = (int) ((float)book.getCover().getHeight() * widthFactor);
			} else {
				width = (int) ((float)book.getCover().getWidth() * heightFactor);
				height = (int) ((float)book.getCover().getHeight() * heightFactor);
			}
			
			int startX = (getWidth() - width) / 2;
			int startY = getHeight() - height;
			
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			
			BufferedImage coverImage = ImageUtilities.getOptimalScalingImage(book.getCover(), width, height);
			
			g2.setStroke(new BasicStroke(2.5f));
			if(isSelected) {
				g2.setColor(new Color(0, 0, 0, 140));
			} else {
				g2.setColor(new Color(0, 0, 0, 100));
			}
			g2.fillOval(startX - 8, startY + coverImage.getHeight() - 8, coverImage.getWidth() + 16, 8);
			
			Polygon shape = new Polygon();
			shape.addPoint(startX - 8, startY + coverImage.getHeight() - 4);
			shape.addPoint(startX - 8 + coverImage.getWidth() + 16, startY + coverImage.getHeight() - 4);
			
			
			shape.addPoint(startX + coverImage.getWidth() + coverImage.getWidth() / 20, startY + coverImage.getHeight() / 20);
			shape.addPoint(startX + coverImage.getWidth(), startY - 3);
			
			shape.addPoint(startX, startY - 2);
			shape.addPoint(startX - coverImage.getWidth() / 20, startY + coverImage.getHeight() / 20);
			
			g2.fill(shape);
			
			g2.setColor(new Color(0, 0, 0, 80));
			g2.setStroke(new BasicStroke(0.8f));
			g2.drawImage(coverImage, startX, startY - 2, null);
			g2.drawRect(startX, startY - 2, coverImage.getWidth(), coverImage.getHeight());
			g2.dispose();
		}
	}
}
