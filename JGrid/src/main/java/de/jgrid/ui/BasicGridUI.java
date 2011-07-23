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

/**
 * A basic L&F implementation of GridUI.  This implementation 
 * is not static, i.e. there's  one UIView implementation for every JGrid objects.
 * @author hendrikebbers
 *
 */
public class BasicGridUI extends GridUI {

	protected JGrid grid;
	private int columnCount = -1;
	private int rowCount = -1;
	private Map<Integer, Rectangle> cellBounds;
	private BasicGridUIHandler handler;
	private CellRendererPane rendererPane;
	private boolean dirtyCellBounds;
	
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		if (c instanceof JGrid) {
			grid = (JGrid) c;
		}
		cellBounds = new HashMap<Integer, Rectangle>();
		dirtyCellBounds = true;

		rendererPane = new CellRendererPane();
		grid.add(rendererPane);

		handler = new BasicGridUIHandler(grid);
		grid.addMouseListener(handler);
		grid.addComponentListener(handler);
		grid.addKeyListener(handler);
		grid.addListDataListener(handler);
		grid.addListSelectionListener(handler);
		grid.addPropertyChangeListener(handler);
	}

	@Override
	public void uninstallUI(JComponent c) {
		grid.remove(rendererPane);
		grid.removeMouseListener(handler);
		grid.removeComponentListener(handler);
		grid.removeKeyListener(handler);
		grid.removeListDataListener(handler);
		grid.removeListSelectionListener(handler);
		grid.removePropertyChangeListener(handler);
		cellBounds.clear();
		handler = null;
		rendererPane = null;
		cellBounds = null;
		grid = null;
	}

	@Override
	public int getCellAt(Point point) {
		maybeUpdateCellBounds();
		for (Entry<Integer, Rectangle> entry : cellBounds.entrySet()) {
			if (entry.getValue().contains(point)) {
				return entry.getKey().intValue();
			}
		}
		return -1;
	}

	/**
	 * Helper for debbuging. Returns true if systemproperty "jgrid.debug" is set to "true"
	 * @return true if debugMode is active
	 */
	protected boolean isDebugMode() {
		return "true".equals(System.getProperty("jgrid.debug", "false"));
	}

	/**
	 * Return the index or -1 if the index is not in the range of the ListModel
	 * 
	 * @param index 
	 * @param grid 
	 * @return the index or -1
	 */
	private int adjustIndex(int index, JGrid grid) {
		return index < grid.getModel().getSize() ? index : -1;
	}

	/**
	 * Returns the preferred height of the JGrid for the specified width
	 * @param width the given width
	 * @return the preferred height
	 */
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
		return new Dimension(width, height);
	}

	private int calcStartX() {
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
			while (aktWidth > widthOneCell) {
				aktWidth = aktWidth - widthOneCell;
				startX = aktWidth;
			}
		}
		return startX;
	}
	
	private void maybeUpdateCellBounds() {
		if(dirtyCellBounds) {
			updateCellBounds();
			dirtyCellBounds = false;
		}
	}
	
	private void updateCellBounds() {
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
		dirtyCellBounds = false;
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		// TODO: Label mit einbauen
		maybeUpdateCellBounds();
			
		for (int i = 0; i < grid.getModel().getSize(); i++) {
			int leadIndex = adjustIndex(grid.getLeadSelectionIndex(), grid);
			if (grid.getVisibleRect().intersects(cellBounds.get(new Integer(i)))) {
				paintCell(g, c, i, cellBounds.get(new Integer(i)), leadIndex);
				paintCellBorder(g, c, i, cellBounds.get(new Integer(i)), leadIndex);
			}
		}
		rendererPane.removeAll();
	}

	/**
     * Paints the Border for the specified cell.
     * Subclasses should override this method and use the specified <code>Graphics</code> object to render the Border of the cell.
     *
     * @param g the <code>Graphics</code> context in which to paint
     * @param c the JGrid being painted
     * @param index the cellindex
     * @param bounds the bounds of the cell
     *
     */ 
	protected void paintCellBorder(Graphics g, JComponent c,
			int index, Rectangle bounds, int leadIndex) {
	}

	/**
     * Paints the specified cell.
     * Subclasses should override this method and use the specified <code>Graphics</code> object to render the cell.
     *
     * @param g the <code>Graphics</code> context in which to paint
     * @param c the JGrid being painted
     * @param index the cellindex
     * @param bounds the bounds of the cell
     *
     */ 
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

	@Override
	public Rectangle getCellBounds(int index) {
		maybeUpdateCellBounds();
		return cellBounds.get(new Integer(index));
	}

	@Override
	public int getColumnCount() {
		maybeUpdateCellBounds();
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
		maybeUpdateCellBounds();
		return rowCount;
	}

	@Override
	public int getRowForIndex(int selectedIndex) {
		maybeUpdateCellBounds();
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

	@Override
	public void markCellBoundsAsDirty() {
		dirtyCellBounds = true;
	}
}