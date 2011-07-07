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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import de.jgrid.JGrid;

public class BasicGridUI extends GridUI {

	protected JGrid grid;
	private int columnCount = -1;
	private int rowCount = -1;
	private Map<Integer, Rectangle> cellBounds;
	private KeyAdapter keyInputHandler;
	private MouseAdapter mouseInputHandler;
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

		mouseInputHandler = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					int index = getCellAt(e.getPoint());
					if (index >= 0) {
						grid.requestFocus();
					}
					if(e.isControlDown()) {
						//TODO: cmd on Mac
						grid.getSelectionModel().addSelectionInterval(index, index);
					} else if(e.isShiftDown()){
						grid.getSelectionModel().addSelectionInterval(grid.getSelectionModel().getLeadSelectionIndex(), index);
					}else {
						grid.setSelectedIndex(index);
					}
				
				}
				//TODO: Wenn selection nicht sichtbar View anpassen
				
				// TODO: nur alte & neue Selektion repainten...
				grid.repaint();
			}
		};

		keyInputHandler = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					int nextIndex = Math.max(0, grid.getSelectedIndex() - 1);
					grid.setSelectedIndex(nextIndex);
					// TODO: nur alte & neue Selektion repainten...
					grid.repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					int nextIndex = Math.min(grid.getModel().getSize(),
							grid.getSelectedIndex() + 1);
					grid.setSelectedIndex(nextIndex);
					// TODO: nur alte & neue Selektion repainten...
					grid.repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					int selectedIndex = grid.getSelectedIndex();
					int row = getRowForIndex(selectedIndex);
					int column = getColumnForIndex(selectedIndex);

					int nextIndex = Math.min(grid.getModel().getSize() - 1,
							getIndexAt(row + 1, column));
					grid.setSelectedIndex(nextIndex);
					// TODO: nur alte & neue Selektion repainten...
					grid.repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					int selectedIndex = grid.getSelectedIndex();
					int row = getRowForIndex(selectedIndex);
					int column = getColumnForIndex(selectedIndex);
					int nextIndex = Math.max(0, getIndexAt(row - 1, column));
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

	@Override
	public void paint(Graphics g, JComponent c) {
		// TODO: Label mit einbauen
		cellBounds.clear();
		int x = 0;
		int y = grid.getVerticalMargin() + grid.getInsets().top;
		int row = 0;
		int indexInRow = 0;

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

			if (isDebugMode()) {
				g.setColor(Color.red);
				g.drawLine(x, y + grid.getFixedCellDimension() / 2,
						x + grid.getHorizonztalMargin(),
						y + grid.getFixedCellDimension() / 2);
				g.drawLine(x, y + grid.getFixedCellDimension() / 2 - 5, x, y
						+ grid.getFixedCellDimension() / 2 + 5);
			}
			if (isDebugMode()) {
				g.setColor(Color.red);
				g.drawLine(
						x + grid.getHorizonztalMargin()
								+ grid.getFixedCellDimension() / 2,
						y,
						x + grid.getHorizonztalMargin()
								+ grid.getFixedCellDimension() / 2,
						y - grid.getVerticalMargin());
				g.drawLine(
						x + grid.getHorizonztalMargin()
								+ grid.getFixedCellDimension() / 2 - 5,
						y - grid.getVerticalMargin(),
						x + grid.getHorizonztalMargin()
								+ grid.getFixedCellDimension() / 2 + 5, y
								- grid.getVerticalMargin());
				g.drawLine(
						x + grid.getHorizonztalMargin()
								+ grid.getFixedCellDimension() / 2,
						y + grid.getFixedCellDimension(),
						x + grid.getHorizonztalMargin()
								+ grid.getFixedCellDimension() / 2,
						y + grid.getFixedCellDimension()
								+ grid.getVerticalMargin());
				g.drawLine(
						x + grid.getHorizonztalMargin()
								+ grid.getFixedCellDimension() / 2 - 5,
						y + grid.getFixedCellDimension()
								+ grid.getVerticalMargin(),
						x + grid.getHorizonztalMargin()
								+ grid.getFixedCellDimension() / 2 + 5,
						y + grid.getFixedCellDimension()
								+ grid.getVerticalMargin());
			}
			x = x + grid.getHorizonztalMargin();
			Rectangle r = new Rectangle(x, y, grid.getFixedCellDimension(),
					grid.getFixedCellDimension());
			x = x + grid.getHorizonztalMargin() + grid.getFixedCellDimension();
			if (isDebugMode()) {
				g.setColor(Color.red);
				g.drawLine(x, y + grid.getFixedCellDimension() / 2,
						x - grid.getHorizonztalMargin(),
						y + grid.getFixedCellDimension() / 2);
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
		rowCount = row + 1;
		rendererPane.removeAll();
	}

	protected void paintCellBorder(Graphics g, JComponent c,
			int index, Rectangle bounds, int leadIndex) {
		//TODO: Paint default border
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