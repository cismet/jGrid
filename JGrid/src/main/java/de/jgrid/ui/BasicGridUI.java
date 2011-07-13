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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import de.jgrid.JGrid;

public class BasicGridUI extends GridUI {

	protected JGrid grid;
	private int columnCount = -1;
	private int rowCount = -1;
	private Map<Integer, Rectangle> cellBounds;
	private BasicGridUIHandler handler;
	private CellRendererPane rendererPane;

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		if (c instanceof JGrid) {
			grid = (JGrid) c;
		}
		cellBounds = new HashMap<Integer, Rectangle>();

		rendererPane = new CellRendererPane();
		grid.add(rendererPane);

		handler = new BasicGridUIHandler(grid);
		grid.addMouseListener(handler);
		grid.addKeyListener(handler);
		grid.addListDataListener(handler);
		grid.addListSelectionListener(handler);
	}

	public void uninstallUI(JComponent c) {
		grid.remove(rendererPane);
		grid.removeMouseListener(handler);
		grid.removeKeyListener(handler);
		grid.removeListDataListener(handler);
		grid.removeListSelectionListener(handler);
		cellBounds.clear();
		handler = null;
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

	protected boolean isDebugMode() {
		return "true".equals(System.getProperty("jgrid.debug", "false"));
	}

	/**
	 * Return the index or -1 if the index is not in the range of the ListModel
	 * 
	 * @param index
	 * @param grid
	 * @return
	 */
	private int adjustIndex(int index, JGrid grid) {
		return index < grid.getModel().getSize() ? index : -1;
	}

	protected int getPreferredHeightForWidth(int width) {
		// TODO: Label mit einbauen
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

	protected int calcStartX() {
		// Damit Zentriert, wird Start-X abhängig von breite gesetzt
		// TODO: grid.INSETS beachten!!!!!
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
		return startX;
	}
	
	@Override
	public void updateCellBounds() {
		cellBounds.clear();
		int x = 0;
		int y = grid.getVerticalMargin() + grid.getInsets().top;
		int row = 0;
		int indexInRow = 0;
		int startX = calcStartX();
		x = startX + grid.getInsets().left;
		for (int i = 0; i < grid.getModel().getSize(); i++) {
			if (x + grid.getHorizonztalMargin() + grid.getHorizonztalMargin()
					+ grid.getFixedCellDimension() > grid.getWidth()
					&& indexInRow > 0) {
				columnCount = indexInRow;
				row++;
				indexInRow = 0;
				x = startX;
				y = y + grid.getVerticalMargin() + grid.getVerticalMargin()
						+ grid.getFixedCellDimension();
			}
			indexInRow++;

			x = x + grid.getHorizonztalMargin();
			Rectangle r = new Rectangle(x, y, grid.getFixedCellDimension(),
					grid.getFixedCellDimension());
			x = x + grid.getHorizonztalMargin() + grid.getFixedCellDimension();
			
			cellBounds.put(new Integer(i), r);
		}
		rowCount = row + 1;
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		// TODO: Label mit einbauen
		updateCellBounds();
			
		for (int i = 0; i < grid.getModel().getSize(); i++) {
			
			if (isDebugMode()) {
//				g.setColor(Color.red);
//				g.drawLine(x, y + grid.getFixedCellDimension() / 2,
//						x + grid.getHorizonztalMargin(),
//						y + grid.getFixedCellDimension() / 2);
//				g.drawLine(x, y + grid.getFixedCellDimension() / 2 - 5, x, y
//						+ grid.getFixedCellDimension() / 2 + 5);
//
//				g.setColor(Color.red);
//				g.drawLine(
//						x + grid.getHorizonztalMargin()
//								+ grid.getFixedCellDimension() / 2,
//						y,
//						x + grid.getHorizonztalMargin()
//								+ grid.getFixedCellDimension() / 2,
//						y - grid.getVerticalMargin());
//				g.drawLine(
//						x + grid.getHorizonztalMargin()
//								+ grid.getFixedCellDimension() / 2 - 5,
//						y - grid.getVerticalMargin(),
//						x + grid.getHorizonztalMargin()
//								+ grid.getFixedCellDimension() / 2 + 5, y
//								- grid.getVerticalMargin());
//				g.drawLine(
//						x + grid.getHorizonztalMargin()
//								+ grid.getFixedCellDimension() / 2,
//						y + grid.getFixedCellDimension(),
//						x + grid.getHorizonztalMargin()
//								+ grid.getFixedCellDimension() / 2,
//						y + grid.getFixedCellDimension()
//								+ grid.getVerticalMargin());
//				g.drawLine(
//						x + grid.getHorizonztalMargin()
//								+ grid.getFixedCellDimension() / 2 - 5,
//						y + grid.getFixedCellDimension()
//								+ grid.getVerticalMargin(),
//						x + grid.getHorizonztalMargin()
//								+ grid.getFixedCellDimension() / 2 + 5,
//						y + grid.getFixedCellDimension()
//								+ grid.getVerticalMargin());
//			
//				g.setColor(Color.red);
//				g.drawLine(x, y + grid.getFixedCellDimension() / 2,
//						x - grid.getHorizonztalMargin(),
//						y + grid.getFixedCellDimension() / 2);
//				g.drawLine(x, y + grid.getFixedCellDimension() / 2 - 5, x, y
//						+ grid.getFixedCellDimension() / 2 + 5);
			}
			int leadIndex = adjustIndex(grid.getLeadSelectionIndex(), grid);
			if (grid.getVisibleRect().intersects(cellBounds.get(new Integer(i)))) {
				paintCell(g, c, i, cellBounds.get(new Integer(i)), leadIndex);
				paintCellBorder(g, c, i, cellBounds.get(new Integer(i)), leadIndex);
			}
		}
		rendererPane.removeAll();
	}

	protected void paintCellBorder(Graphics g, JComponent c,
			int index, Rectangle bounds, int leadIndex) {
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
	
		Object value = grid.getModel().getElementAt(index);

		Component rendererComponent = grid.getCellRenderer(index)
				.getGridCellRendererComponent(grid, value, index, isSelected,
						cellHasFocus);
		rendererPane.paintComponent(g, rendererComponent, grid, bounds.x, bounds.y,
				bounds.width, bounds.height, true);
	}

	public Rectangle getCellBounds(int index) {
		return cellBounds.get(new Integer(index));
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public int getIndexAt(int row, int column) {
		if (row < 0 || column < 0) {
			return -1;
		}
		int index = 0;
		if (row > 0) {
			index = row * getColumnCount();
		}
		return index + column;
	}

	@Override
	public int getRowCount() {
		return rowCount;
	}

	@Override
	public int getRowForIndex(int selectedIndex) {
		return selectedIndex / columnCount;
	}

	@Override
	public int getColumnForIndex(int selectedIndex) {
		int row = getRowForIndex(selectedIndex);
		if (row == 0) {
			return selectedIndex;
		}
		int prev = (row * getColumnCount());
		int index = selectedIndex - prev;
		return index;
	}
}