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
package de.jgrid.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * A default UI for the Grid. The UI is for MAC OS and the rendering looks like the grid in iPhoto.
 * @author Hendrik Ebbers
 * @since 0.1
 * @version 0.1
 */
public class MacOsGridUI extends BasicGridUI {

	private BufferedImage offScreenImage;
	private Stroke unselectedBorderStroke = new BasicStroke(1.8f);
	private Stroke selectedBorderForegroundStroke = new BasicStroke(4);
	private Stroke selectedBorderBackgroundStroke = new BasicStroke(6);
	private int selectionArcWidth = 20;
	private int selectionArcHeight = 20;
		
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		grid.setBackground(new Color(103,103,103));
		grid.setOpaque(true);
		grid.setSelectionBorderColor(new Color(248,211,80));
		grid.setSelectionForeground(new Color(0,0,0));
		grid.setSelectionBackground(new Color(43,43,43));
		grid.setCellBackground(new Color(43,43,43));
	}
	
	protected void paintCell(Graphics g, JComponent c, int index,
			Rectangle bounds, int leadIndex) {
		boolean cellHasFocus = grid.hasFocus() && (index == leadIndex);
		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);

		if (offScreenImage == null || offScreenImage.getWidth() != bounds.width
				|| offScreenImage.getHeight() != bounds.height) {
			offScreenImage = new BufferedImage(bounds.width, bounds.height,
					BufferedImage.TYPE_INT_ARGB);
		}
		Graphics2D g2 = offScreenImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Clear
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, offScreenImage.getWidth(), offScreenImage.getHeight());

		// CLip
		g2.setComposite(AlphaComposite.SrcOver);
		if (isSelected) {
			g2.setColor(grid.getSelectionBackground());
		} else {
			g2.setColor(grid.getCellBackground());
		}
		g2.fillRoundRect(0, 0, bounds.width, bounds.height, selectionArcWidth,
				selectionArcHeight);

		// Content
		g2.setComposite(AlphaComposite.SrcIn);
		if (isDebugMode()) {
			g2.setColor(Color.blue);
			g2.fillRect(0, 0, bounds.width, bounds.height);
		}
		Object value = grid.getModel().getElementAt(index);

		Component rendererComponent = grid.getCellRenderer(index)
				.getGridCellRendererComponent(grid, value, index, isSelected,
						cellHasFocus);
		getRendererPane().paintComponent(g2, rendererComponent, grid, 0, 0,
				bounds.width, bounds.height, true);

		g2.dispose();
		g.drawImage(offScreenImage, bounds.x, bounds.y, null);
	}
	
	protected void paintCellBorder(Graphics g, JComponent c, int index,
			Rectangle bounds, int leadIndex) {
		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (isSelected) {
			g2.setColor(grid.getSelectionBackground());
			g2.setStroke(selectedBorderBackgroundStroke);
			g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height,
					selectionArcWidth, selectionArcHeight);

			g2.setColor(grid.getSelectionBorderColor());
			g2.setStroke(selectedBorderForegroundStroke);
			g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height,
					selectionArcWidth, selectionArcHeight);
		} else {
			g2.setColor(grid.getCellBackground());
			g2.setStroke(unselectedBorderStroke);
			g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height,
					selectionArcWidth, selectionArcHeight);
		}
		g2.dispose();
	}

	protected void paintCellLabel(Graphics g, JComponent c, int index,
			Rectangle bounds, int leadIndex) {
		boolean cellHasFocus = grid.hasFocus() && (index == leadIndex);
		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);

		Object value = grid.getModel().getElementAt(index);

		Component rendererComponent = grid.getDefaultLabelRenderer()
				.getGridLabelRendererComponent(grid, value, index, isSelected,
						cellHasFocus);
		getRendererPane().paintComponent(g, rendererComponent, grid, 0, 0,
				bounds.width, bounds.height, true);
	}
}
