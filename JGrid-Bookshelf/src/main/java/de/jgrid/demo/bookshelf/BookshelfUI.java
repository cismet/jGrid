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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

import de.jgrid.GridUI;
import de.jgrid.demo.util.UrlLoader;

public class BookshelfUI extends GridUI {

	BufferedImage backgroundImage;
	
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		try {			
			backgroundImage = ImageIO.read(UrlLoader.getInstance().load("/de/jgrid/demo/bookshelf/bookshelf.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		grid.setFixedCellDimension(120);
		grid.setHorizonztalMargin(4);
		grid.setVerticalMargin(12);
		grid.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		grid.setSelectionBackground(null);
		grid.setCellBackground(null);
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		int height = 0;
		int width = 0;
		
		while(height < c.getHeight()) {
			width = 0;
			while(width < c.getWidth()) {
				g.drawImage(backgroundImage, width, height, null);
				width = width + backgroundImage.getWidth();
			}
			height = height + backgroundImage.getHeight();
		}
		super.paint(g, c);
	}
	
	@Override
	protected void paintCellBorder(Graphics g, JComponent c, int index,
			Rectangle bounds, int leadIndex) {
		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);
		if(isSelected) {
			bounds.y = bounds.y - 5;
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			int lightHeight = bounds.height - (bounds.height / 10);
			int topWidth = (bounds.width / 10);
			int lightWidth = (bounds.width / 20);
			
			Polygon shape = new Polygon();
			shape.addPoint(bounds.x + (bounds.width / 2) - (topWidth / 2), bounds.y);
			shape.addPoint(bounds.x + lightWidth, bounds.y + lightHeight);
			shape.addPoint(bounds.x + bounds.width - lightWidth, bounds.y + lightHeight);
			shape.addPoint(bounds.x + (bounds.width / 2) + (topWidth / 2), bounds.y);
			
			

			g2.setStroke(new BasicStroke(1.5f));
			g2.setPaint(new GradientPaint(bounds.x, bounds.y, new Color(255, 255, 255, 130), bounds.x, bounds.y + lightHeight, new Color(255, 255, 255, 0)));
			g2.fill(shape);
			g2.draw(shape);
			
			RadialGradientPaint radialGradient = new RadialGradientPaint(new Point(bounds.x + (bounds.width / 2),bounds.y), lightHeight / 2, new float[]{0.0f, 1.0f}, new Color[]{new Color(255, 255, 255), new Color(255, 255, 255, 0)});
			g2.setPaint(radialGradient);
			g2.fill(shape);
			
			g2.dispose();
		}
	}
	
	protected void paintCell(Graphics g, JComponent c, int index,
			Rectangle bounds, int leadIndex) {
		boolean cellHasFocus = grid.hasFocus() && (index == leadIndex);
		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);

		Graphics2D g2 = (Graphics2D) g.create();
		if (debug) {
			g2.setColor(Color.blue);
			g2.fillRect(0, 0, bounds.width, bounds.height);
		}
		Object value = grid.getModel().getElementAt(index);

		Component rendererComponent = grid.getDefaultCellRenderer()
				.getGridCellRendererComponent(grid, value, index, isSelected,
						cellHasFocus);
		rendererPane.add(rendererComponent);
		rendererPane.paintComponent(g2, rendererComponent, grid, bounds.x, bounds.y,
				bounds.width, bounds.height, true);

		g2.dispose();
	}
}
