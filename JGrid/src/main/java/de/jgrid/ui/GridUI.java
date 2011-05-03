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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;

import de.jgrid.JGrid;

public abstract class GridUI extends ComponentUI {

	protected JGrid grid;
	protected boolean debug = false;
	protected Stroke selectedBorderForegroundStroke = new BasicStroke(4);
	protected Stroke selectedBorderBackgroundStroke = new BasicStroke(6);
	private Stroke unselectedBorderStroke = new BasicStroke(1.8f);
	protected CellRendererPane rendererPane;
	protected int selectionArcWidth = 20;
	protected int selectionArcHeight = 20;
	private BufferedImage offScreenImage;
	protected Map<Integer, Rectangle> cellBounds;
	protected KeyAdapter keyInputHandler;
	protected MouseAdapter mouseInputHandler;

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		if (c instanceof JGrid) {
			grid = (JGrid) c;
		}
		cellBounds = new HashMap<Integer, Rectangle>();

		rendererPane = new CellRendererPane();
		grid.add(rendererPane);

		mouseInputHandler = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = getCellAt(e.getPoint());
				if (index >= 0) {
					grid.requestFocus();
				}
				grid.setSelectedIndex(index);

				// TODO: nur alte & neue Selektion repainten...
				grid.repaint();
			}
		};

		keyInputHandler = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_LEFT) {
					int nextIndex = Math.max(0, grid.getSelectedIndex() - 1);
					grid.setSelectedIndex(nextIndex);
					// TODO: nur alte & neue Selektion repainten...
					grid.repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					int nextIndex = Math.min(grid.getModel().getSize(), grid
							.getSelectedIndex() + 1);
					grid.setSelectedIndex(nextIndex);
					// TODO: nur alte & neue Selektion repainten...
					grid.repaint();
				}
			}
		};

		grid.addMouseListener(mouseInputHandler);
		grid.addKeyListener(keyInputHandler);
	}

	public void uninstallUI(JComponent c) {
		grid.remove(rendererPane);
		grid.removeMouseListener(mouseInputHandler);
		grid.removeKeyListener(keyInputHandler);
		cellBounds.clear();
		mouseInputHandler = null;
		keyInputHandler = null;
		rendererPane = null;
		cellBounds = null;
		grid = null;
	}

	public int getCellAt(Point point) {
		for (Entry<Integer, Rectangle> entry : cellBounds.entrySet()) {
			if (entry.getValue().contains(point)) {
				return entry.getKey().intValue();
			}
		}
		return -1;
	}

	/**
	 * Return the index or -1 if the oindex is not in the range of the ListModel
	 * @param index
	 * @param grid
	 * @return
	 */
	private int adjustIndex(int index, JGrid grid) {
		return index < grid.getModel().getSize() ? index : -1;
	}
	
	protected int getPreferredHeightForWidth(int width) {
		//TODO: Label mit einbauen
		int cellsInRow = 0;
		int widthOneCell = grid.getHorizonztalMargin()
				+ grid.getHorizonztalMargin() + grid.getFixedCellDimension();
		int aktWidth = width;
		if (aktWidth > widthOneCell) {
			while (aktWidth > widthOneCell) {
				aktWidth = aktWidth - widthOneCell;
				cellsInRow++;
			}
		} else {
			cellsInRow = 1;
		}
		int rows = grid.getModel().getSize() / cellsInRow;
		if (grid.getModel().getSize() % cellsInRow > 0) {
			rows++;
		}
		int heightOneCell = grid.getVerticalMargin() + grid.getVerticalMargin()
				+ grid.getFixedCellDimension();
		return rows * heightOneCell;
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		int widthOneCell = grid.getHorizonztalMargin()
				+ grid.getHorizonztalMargin() + grid.getFixedCellDimension();
		int width = Math.max(c.getWidth(), widthOneCell);
		int height = getPreferredHeightForWidth(width);
		if (grid.getInsets() != null) {
			width = grid.getInsets().left + width + grid.getInsets().right;
			height = grid.getInsets().top + height + grid.getInsets().bottom;
		}
		return new Dimension(width, getPreferredHeightForWidth(width));
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		//TODO: Label mit einbauen
		cellBounds.clear();
		int x = 0;
		int y = grid.getVerticalMargin() + grid.getInsets().top;
		int row = 0;
		int indexInRow = 0;

		// Damit Zentriert, wird Start-X abhŠngig von breite gesetzt
		//TODO: grid.INSETS beachten!!!!!
		int widthOneCell = grid.getHorizonztalMargin()
				+ grid.getHorizonztalMargin() + grid.getFixedCellDimension();
		int aktWidth = grid.getWidth();
		int startX = 0;
		if (grid.getHorizontalAlignment() == SwingConstants.CENTER) {
			if (aktWidth > widthOneCell) {
				while (aktWidth > widthOneCell) {
					aktWidth = aktWidth - widthOneCell;
				}
				startX = aktWidth / 2;
			}
		} else if (grid.getHorizontalAlignment() == SwingConstants.RIGHT) {
			if (aktWidth > widthOneCell) {
				while (aktWidth > widthOneCell) {
					aktWidth = aktWidth - widthOneCell;
				}
				startX = aktWidth;
			}
		}
		x = startX + grid.getInsets().left;
		for (int i = 0; i < grid.getModel().getSize(); i++) {
			if (x + grid.getHorizonztalMargin() + grid.getHorizonztalMargin()
					+ grid.getFixedCellDimension() > grid.getWidth()
					&& indexInRow > 0) {
				row++;
				indexInRow = 0;
				x = startX;
				y = y + grid.getVerticalMargin() + grid.getVerticalMargin()
						+ grid.getFixedCellDimension();
			}
			indexInRow++;

			if (debug) {
				g.setColor(Color.red);
				g.drawLine(x, y + grid.getFixedCellDimension() / 2, x
						+ grid.getHorizonztalMargin(), y
						+ grid.getFixedCellDimension() / 2);
				g.drawLine(x, y + grid.getFixedCellDimension() / 2 - 5, x, y
						+ grid.getFixedCellDimension() / 2 + 5);
			}
			if (debug) {
				g.setColor(Color.red);
				g.drawLine(x + grid.getHorizonztalMargin()
						+ grid.getFixedCellDimension() / 2, y, x
						+ grid.getHorizonztalMargin()
						+ grid.getFixedCellDimension() / 2, y
						- grid.getVerticalMargin());
				g.drawLine(x + grid.getHorizonztalMargin()
						+ grid.getFixedCellDimension() / 2 - 5, y
						- grid.getVerticalMargin(), x
						+ grid.getHorizonztalMargin()
						+ grid.getFixedCellDimension() / 2 + 5, y
						- grid.getVerticalMargin());
				g.drawLine(x + grid.getHorizonztalMargin()
						+ grid.getFixedCellDimension() / 2, y
						+ grid.getFixedCellDimension(), x
						+ grid.getHorizonztalMargin()
						+ grid.getFixedCellDimension() / 2, y
						+ grid.getFixedCellDimension()
						+ grid.getVerticalMargin());
				g.drawLine(x + grid.getHorizonztalMargin()
						+ grid.getFixedCellDimension() / 2 - 5, y
						+ grid.getFixedCellDimension()
						+ grid.getVerticalMargin(), x
						+ grid.getHorizonztalMargin()
						+ grid.getFixedCellDimension() / 2 + 5, y
						+ grid.getFixedCellDimension()
						+ grid.getVerticalMargin());
			}
			x = x + grid.getHorizonztalMargin();
			Rectangle r = new Rectangle(x, y, grid.getFixedCellDimension(),
					grid.getFixedCellDimension());
			x = x + grid.getHorizonztalMargin() + grid.getFixedCellDimension();
			if (debug) {
				g.setColor(Color.red);
				g.drawLine(x, y + grid.getFixedCellDimension() / 2, x
						- grid.getHorizonztalMargin(), y
						+ grid.getFixedCellDimension() / 2);
				g.drawLine(x, y + grid.getFixedCellDimension() / 2 - 5, x, y
						+ grid.getFixedCellDimension() / 2 + 5);
			}
			int leadIndex = adjustIndex(grid.getLeadSelectionIndex(), grid);
			cellBounds.put(new Integer(i), r);
			if (grid.getVisibleRect().intersects(r)) {
				paintCell(g, c, i, r, leadIndex);
				paintCellBorder(g, c, i, r, leadIndex);
			}
		}
		rendererPane.removeAll();
	}

	protected void paintCellBorder(Graphics g, JComponent c,
			int index, Rectangle bounds, int leadIndex) {
		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if(isSelected) {
			g2.setColor(grid.getSelectionBackground());
			g2.setStroke(selectedBorderBackgroundStroke);
			g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, selectionArcWidth,
					selectionArcHeight);
			
			g2.setColor(grid.getSelectionBorderColor());
			g2.setStroke(selectedBorderForegroundStroke);
			g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height,
					selectionArcWidth, selectionArcHeight);
		} else {
			g2.setColor(grid.getCellBackground());
			g2.setStroke(unselectedBorderStroke);
			g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, selectionArcWidth,
					selectionArcHeight);
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
		rendererPane.paintComponent(g, rendererComponent, grid, 0, 0,
				bounds.width, bounds.height, true);
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
		g2
				.fillRect(0, 0, offScreenImage.getWidth(), offScreenImage
						.getHeight());

		// CLip
		g2.setComposite(AlphaComposite.SrcOver);
		if (isSelected) {
			g2.setColor(grid.getSelectionBackground());
		} else {
			g2.setColor(grid.getCellBackground());
		}
		g2.fillRoundRect(0, 0, bounds.width, bounds.height, selectionArcWidth, selectionArcHeight);

		// Content
		g2.setComposite(AlphaComposite.SrcIn);
		if (debug) {
			g2.setColor(Color.blue);
			g2.fillRect(0, 0, bounds.width, bounds.height);
		}
		Object value = grid.getModel().getElementAt(index);

		Component rendererComponent = grid.getCellRenderer(index)
				.getGridCellRendererComponent(grid, value, index, isSelected,
						cellHasFocus);
		rendererPane.paintComponent(g2, rendererComponent, grid, 0, 0,
				bounds.width, bounds.height, true);

		g2.dispose();
		g.drawImage(offScreenImage, bounds.x, bounds.y, null);
	}

	public Rectangle getCellBounds(int index) {
		return cellBounds.get(new Integer(index));
	}
}