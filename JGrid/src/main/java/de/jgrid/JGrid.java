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
package de.jgrid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

/**
 * A Component that displays a list of Elements in a grid. The Elements stored is  a separate model, {@code ListModel}. So you can use a {@code JList} parallel to to the JGrid
 * 
 * @author hendrikebbers
 *
 */
public class JGrid extends JComponent implements Scrollable, SwingConstants {

	private static final long serialVersionUID = 1L;
	private ListSelectionModel selectionModel;
	private ListModel dataModel;
	private GridCellRenderer defaultCellRenderer;
	private GridLabelRenderer defaultLabelRenderer;
	private int fixedCellDimension = 128;
	private int cellLabelCap = 16;
	private int horizonztalMargin = 16;
	private int verticalMargin = 16;
	private Color selectionForeground;
	private Color selectionBorderColor;
	private Color selectionBackground;
	private Color cellBackground;
	private int horizontalAlignment = CENTER;
	private boolean labelsVisible = true;
	
	private static final String uiClassID = "GridUI";

	/**
     * Constructs a {@code JGrid} that displays elements from the specified,
     * {@code non-null}, model. All {@code JGrid} constructors must delegate to
     * this one.
     *
     * @param model the model for the grid
     * @exception IllegalArgumentException if the model is {@code null}
     */
	public JGrid(ListModel model) {
		if (model == null) {
			throw new IllegalArgumentException("dataModel must be non null");
		}

		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.registerComponent(this);

		this.dataModel = model;
		selectionModel = createDefaultSelectionModel();
		
		setAutoscrolls(true);
		setOpaque(true);
		updateUI();
		setDefaultCellRenderer(new DefaultGridCellRenderer());
	}

	 /**
     * Returns an default instance of {@code ListSelectionModel}; called
     * during construction to initialize the grids selectionModel. Normally this returns a {@code DefaultListSelectionModel}
     *
     * @return a default ListSelectionModel
     */
	protected ListSelectionModel createDefaultSelectionModel() {
		return new DefaultListSelectionModel();
	}

	/**
     * Returns the default GridLabelRenderer
     *
     * @return GridLabelRenderer of this grid
     */
	public GridLabelRenderer getDefaultLabelRenderer() {
		return defaultLabelRenderer;
	}

	/**
	 * Sets the GridLabelRenderer for this Component.
	 * Fires {@PropertyChangeEvent} with the {@defaultLabelRenderer} property
	 * @param defaultLabelRenderer the new LabelRenderer for this Grid
	 */
	public void setDefaultLabelRenderer(GridLabelRenderer defaultLabelRenderer) {
		GridLabelRenderer oldValue = this.defaultLabelRenderer;
		this.defaultLabelRenderer = defaultLabelRenderer;
		firePropertyChange("defaultLabelRenderer", oldValue,
				this.defaultLabelRenderer);
	}

	/**
	 * Getter for the cellLabelCap
	 * @return cellLabelCap
	 */
	public int getCellLabelCap() {
		return cellLabelCap;
	}

	/**
     * Returns {@code true} if the cells in the JGrid are labeled, else {@code false}
     *
     * @return {@code true} if the cells in the JGrid are labeled, else {@code false}
     */
	public boolean isLabelsVisible() {
		return labelsVisible;
	}
	
	/**
	 * Sets the labelsVisible-Property for this Component. If true the grid renders Labels for all Elements. (Actually not working)
	 * Fires {@PropertyChangeEvent} with the {@labelsVisible} property
	 * @param labelsVisible the labelsVisible-flag for this Grid
	 */
	public void setLabelsVisible(boolean labelsVisible) {
		boolean oldValue = this.labelsVisible;
		this.labelsVisible = labelsVisible;
		firePropertyChange("labelsVisible", oldValue, this.labelsVisible);
	}

	/**
	 * Return the Model of the JGrid
	 * @return The Model of the JGrid
	 */
	public ListModel getModel() {
		return dataModel;
	}

	/**
	 * Returns the default renderer for the cells
	 * @return the default renderer
	 */
	public GridCellRenderer getDefaultCellRenderer() {
		return defaultCellRenderer;
	}

	/**
	 * Returns the ListSelectionModel
	 * @return the ListSelectionModel
	 */
	public ListSelectionModel getSelectionModel() {
		return selectionModel;
	}

	public int getSelectedIndex() {
		return  getSelectionModel().getMinSelectionIndex();
	}

	public int getLeadSelectionIndex() {
		return getSelectionModel().getLeadSelectionIndex();
	}

	public GridUI getUI() {
		return (GridUI) ui;
	}

	public void updateUI() {
		setUI(new MacOsGridUI());
		GridCellRenderer renderer = getDefaultCellRenderer();
		if (renderer instanceof Component) {
			SwingUtilities.updateComponentTreeUI((Component) renderer);
		}
	}

	/**
	 * Sets the rendering dimension for all elements in the grid. The width and height of each element is equals the dimenison.
	 * Fires {@PropertyChangeEvent} with the {@fixedCellDimension} property
	 * @param dimension the new dimension for this Grid
	 */
	public void setFixedCellDimension(int dimension) {
		int oldValue = fixedCellDimension;
		fixedCellDimension = dimension;
		firePropertyChange("fixedCellDimension", oldValue, fixedCellDimension);
		revalidate();
		repaint();
	}


	/**
	 * Sets the vertical margin between all elements in the grid.
	 * Fires {@PropertyChangeEvent} with the {@verticalMargin} property
	 * @param verticalMargin the vertical margin for this Grid
	 */
	public void setVerticalMargin(int verticalMargin) {
		int oldValue = verticalMargin;
		this.verticalMargin = verticalMargin;
		firePropertyChange("verticalMargin", oldValue, verticalMargin);
		revalidate();
		repaint();
	}

	/**
	 * Sets the horizonztal margin between all elements in the grid.
	 * Fires {@PropertyChangeEvent} with the {@horizonztalMargin} property
	 * @param horizonztalMargin the horizonztal margin for this Grid
	 */
	public void setHorizonztalMargin(int horizonztalMargin) {
		int oldValue = horizonztalMargin;
		this.horizonztalMargin = horizonztalMargin;
		firePropertyChange("horizonztalMargin", oldValue, horizonztalMargin);
		revalidate();
		repaint();
	}

	public int getFixedCellDimension() {
		return fixedCellDimension;
	}

	public int getHorizonztalMargin() {
		return horizonztalMargin;
	}

	public int getVerticalMargin() {
		return verticalMargin;
	}

	public void setDefaultCellRenderer(GridCellRenderer cellRenderer) {
		GridCellRenderer oldValue = this.defaultCellRenderer;
		this.defaultCellRenderer = cellRenderer;
		firePropertyChange("defaultCellRenderer", oldValue, cellRenderer);
	}

	public String getUIClassID() {
		return uiClassID;
	}

	public void setUI(GridUI ui) {
		super.setUI(ui);
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return getVerticalMargin() + getVerticalMargin()
				+ getFixedCellDimension();
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		if (getParent() instanceof JViewport) {
			return (((JViewport) getParent()).getHeight() > getPreferredSize().height);
		}
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return getVerticalMargin() + getVerticalMargin()
				+ getFixedCellDimension();
	}

	public Color getSelectionForeground() {
		return selectionForeground;
	}

	public void setSelectionForeground(Color selectionForeground) {
		Color oldValue = this.selectionForeground;
		this.selectionForeground = selectionForeground;
		firePropertyChange("selectionForeground", oldValue, selectionForeground);
		repaint();
	}

	public void setSelectionBorderColor(Color selectionBorderColor) {
		Color oldValue = this.selectionBorderColor;
		this.selectionBorderColor = selectionBorderColor;
		firePropertyChange("selectionBorderColor", oldValue,
				selectionBorderColor);
		repaint();
	}

	public Color getSelectionBorderColor() {
		return selectionBorderColor;
	}

	public Color getSelectionBackground() {
		return selectionBackground;
	}

	public void setSelectionBackground(Color selectionBackground) {
		Color oldValue = this.selectionBackground;
		this.selectionBackground = selectionBackground;
		firePropertyChange("selectionBackground", oldValue, selectionBackground);
		repaint();
	}

	public Color getCellBackground() {
		return cellBackground;
	}

	public void setCellBackground(Color cellBackground) {
		Color oldValue = this.cellBackground;
		this.cellBackground = cellBackground;
		firePropertyChange("cellBackground", oldValue, cellBackground);
		repaint();
	}

	public void setSelectedIndex(int index) {
		if (index >= getModel().getSize()) {
			return;
		}
		getSelectionModel().setSelectionInterval(index, index);
	}


	public Rectangle getCellBounds(int index) {
		return getUI().getCellBounds(index);
	}
	
	public int getCellAt(Point point) {
		return getUI().getCellAt(point);
	}
	
	public String getToolTipText(MouseEvent event) {
		if (event != null) {
			Point p = event.getPoint();
			int index = getCellAt(p);
			
			if(index >= 0) {
				Rectangle cellBounds = getCellBounds(index);
				if(cellBounds != null && cellBounds.contains(p.x, p.y)) {
					Component renderer = getDefaultCellRenderer()
					.getGridCellRendererComponent(
							this,
							getModel().getElementAt(index),
							index,
							getSelectionModel().isSelectedIndex(index),
							hasFocus()
									&& getSelectionModel()
											.getLeadSelectionIndex() == index);
					if (renderer instanceof JComponent) {
						return ((JComponent) renderer)
								.getToolTipText(new MouseEvent(renderer, event.getID(), event
										.getWhen(), event.getModifiers(), p.x - cellBounds.x, p.y - cellBounds.y, event
										.getXOnScreen(), event.getYOnScreen(), event
										.getClickCount(), event.isPopupTrigger(), event.getButton()));
					}
				}
			}
		}
		return super.getToolTipText();
	}

	public int getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(int alignment) {
		if ((alignment == LEFT) || (alignment == CENTER) || (alignment == RIGHT)
				|| (alignment == LEADING) || (alignment == TRAILING)) {
			if (alignment == horizontalAlignment) {
				return;
			}
			int oldValue = horizontalAlignment;
			horizontalAlignment = alignment;
			firePropertyChange("horizontalAlignment", oldValue, horizontalAlignment);
			repaint();
		} else {
			throw new IllegalArgumentException("Illegal HorizontalAlignment: " + alignment);
		}
	}
}