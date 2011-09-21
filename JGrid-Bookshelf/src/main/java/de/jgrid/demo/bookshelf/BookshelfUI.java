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

import com.guigarage.jgrid.ui.BasicGridUI;

import de.jgrid.demo.util.UrlLoader;

public class BookshelfUI extends BasicGridUI {

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
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			Rectangle spotlightRect = new Rectangle(bounds.getLocation(), bounds.getSize());
			spotlightRect.y = spotlightRect.y - 5;
			
			int lightHeight = spotlightRect.height - (spotlightRect.height / 10);
			int topWidth = (spotlightRect.width / 10);
			int lightWidth = (spotlightRect.width / 20);
			
			Polygon shape = new Polygon();
			shape.addPoint(spotlightRect.x + (spotlightRect.width / 2) - (topWidth / 2), spotlightRect.y);
			shape.addPoint(spotlightRect.x + lightWidth, spotlightRect.y + lightHeight);
			shape.addPoint(spotlightRect.x + spotlightRect.width - lightWidth, spotlightRect.y + lightHeight);
			shape.addPoint(spotlightRect.x + (spotlightRect.width / 2) + (topWidth / 2), spotlightRect.y);
			
			

			g2.setStroke(new BasicStroke(1.5f));
			g2.setPaint(new GradientPaint(spotlightRect.x, spotlightRect.y, new Color(255, 255, 255, 130), spotlightRect.x, spotlightRect.y - 5 + lightHeight, new Color(255, 255, 255, 0)));
			g2.fill(shape);
			g2.draw(shape);
			
			RadialGradientPaint radialGradient = new RadialGradientPaint(new Point(spotlightRect.x + (spotlightRect.width / 2),spotlightRect.y), lightHeight / 2, new float[]{0.0f, 1.0f}, new Color[]{new Color(255, 255, 255), new Color(255, 255, 255, 0)});
			g2.setPaint(radialGradient);
			g2.fill(shape);
			
			g2.dispose();
		}
	}
	
}
