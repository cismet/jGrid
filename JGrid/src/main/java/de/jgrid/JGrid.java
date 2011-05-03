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

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JViewport;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.jgrid.renderer.GridCellRenderer;
import de.jgrid.renderer.GridCellRendererManager;
import de.jgrid.renderer.GridLabelRenderer;
import de.jgrid.renderer.GridRendererManagerListener;
import de.jgrid.sort.ListSorter;
import de.jgrid.sort.ListSorterEvent;
import de.jgrid.sort.ListSorterListener;
import de.jgrid.ui.GridUI;
import de.jgrid.ui.MacOsGridUI;

/**
 * A Component that displays a list of Elements in a grid. The Elements stored is  a separate model, {@code ListModel}. So you can use a {@code JList} parallel to to the JGrid
 * 
 * @author Hendrik Ebbers
 * @since 0.1
 * @version 0.1
 * @see JList
 */
public class JGrid extends JComponent implements Scrollable, SwingConstants, ListDataListener, GridRendererManagerListener, ListSorterListener {

	private static final long serialVersionUID = 1L;
	private ListSelectionModel selectionModel;
	private ListModel model;
	private GridCellRendererManager cellRendererManager;
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
	
	private ListSorter sorter;
	
	private static final String uiClassID = "GridUI";

	public JGrid() {
		this(new DefaultListModel());
	}
	
	/**
     * Constructs a {@code JGrid} that displays elements from the specified,
     * {@code non-null}, model. All {@code JGrid} constructors must delegate to
     * this one.
     *
     * @param model the model for the grid
     * @exception IllegalArgumentException if the model is {@code null}
     * @since 0.1
     */
	public JGrid(ListModel model) {
		if (model == null) {
			throw new IllegalArgumentException("dataModel must be non null");
		}

		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.registerComponent(this);

		selectionModel = createDefaultSelectionModel();
		setModel(model);
//		setSorter(new DefaultListSorter());
		
		setAutoscrolls(true);
		setOpaque(true);
		setCellRendererManager(new GridCellRendererManager());
		updateUI();
	}
	
	public void setSorter(ListSorter sorter) {
		
		ListSorter oldSorter = this.sorter;
		
		if(oldSorter != null) {
			oldSorter.removeListSorterListener(this);
			getModel().removeListDataListener(oldSorter);
			oldSorter.setModel(null);
		}
		
		this.sorter = sorter;
		if(this.sorter != null) {
			this.sorter.addListSorterListener(this);
			this.sorter.setModel(model);
			getModel().addListDataListener(this.sorter);
		}
		
		firePropertyChange("sorter", oldSorter, this.sorter);
		
		resizeAndRepaint();
	}
	
	public ListSorter getSorter() {
		return sorter;
	}
	
	public void setCellRendererManager(
			GridCellRendererManager cellRendererManager) {
		if(cellRendererManager == null) {
			throw new IllegalArgumentException("cellRendererManager must be non null");
		}
		
		GridCellRendererManager oldManager = this.cellRendererManager;
		if(oldManager != null) {
			oldManager.removeGridRendererManagerListener(this);
		}
		
		this.cellRendererManager = cellRendererManager;
		this.cellRendererManager.removeGridRendererManagerListener(this);
		cellRendererManager.updateRendererUI();
		
		firePropertyChange("cellRendererManager", oldManager, this.cellRendererManager);
		
		resizeAndRepaint();
	}
	
	public GridCellRendererManager getCellRendererManager() {
		return cellRendererManager;
	}
	
    public void setModel(ListModel model) {
        if (model == null) {
            throw new IllegalArgumentException("model must be non null");
        }
        ListModel oldModel = this.model;
        if(oldModel != null) {
        	oldModel.removeListDataListener(this);
        	oldModel.removeListDataListener(getSorter());
        }
        this.model = model;
        this.model.addListDataListener(this);
        
        if(this.sorter != null) {
        	this.model.addListDataListener(sorter);
            this.sorter.setModel(this.model);
        }
        firePropertyChange("model", oldModel, this.model);
        selectionModel.clearSelection();
    }

	public int getViewCellCount() {
		if(getSorter() != null) {
			return getSorter().getViewCellCount();
		}
		return getModel().getSize();
	}
	
	public int convertCellIndexToModel(int index) {
		if(getSorter() != null) {
			return getSorter().convertCellIndexToModel(index);
		}
		return index;
	}
	
	public int convertCellIndexToView(int index) {
		if(getSorter() != null) {
			return getSorter().convertCellIndexToView(index);
		}
		return index;
	}
    
    protected void resizeAndRepaint() {
        revalidate();
        repaint();
    }
	
	 /**
     * Returns an default instance of {@code ListSelectionModel}; called
     * during construction to initialize the grids selectionModel. Normally this returns a {@code DefaultListSelectionModel}
     *
     * @return a default ListSelectionModel
     * @since 0.1
     * @see JList
     */
	protected ListSelectionModel createDefaultSelectionModel() {
		return new DefaultListSelectionModel();
	}

	/**
     * Returns the default GridLabelRenderer
     *
     * @return GridLabelRenderer of this grid
     * @since 0.1
     */
	public GridLabelRenderer getDefaultLabelRenderer() {
		return defaultLabelRenderer;
	}

	/**
	 * Sets the GridLabelRenderer for this Component.
	 * Fires <code>PropertyChangeEvent</code> with the <code>defaultLabelRenderer</code> propertyname
	 * @param defaultLabelRenderer the new LabelRenderer for this Grid
	 * @since 0.1
	 * 
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
	 * @since 0.1
	 */
	public int getCellLabelCap() {
		return cellLabelCap;
	}

	/**
     * Returns {@code true} if the cells in the JGrid are labeled, else {@code false}
     *
     * @return {@code true} if the cells in the JGrid are labeled, else {@code false}
     * @since 0.1
     */
	public boolean isLabelsVisible() {
		return labelsVisible;
	}
	
	/**
	 * Sets the labelsVisible-Property for this Component. If true the grid renders Labels for all Elements. (Actually not working)
	 * Fires <code>PropertyChangeEvent</code> with the <code>labelsVisible</code> propertyname
	 * @param labelsVisible the labelsVisible-flag for this Grid
	 * @since 0.1
	 */
	public void setLabelsVisible(boolean labelsVisible) {
		boolean oldValue = this.labelsVisible;
		this.labelsVisible = labelsVisible;
		firePropertyChange("labelsVisible", oldValue, this.labelsVisible);
	}

	/**
	 * Return the Model of the JGrid
	 * @return The Model of the JGrid
	 * @since 0.1
	 * @see JList
	 */
	public ListModel getModel() {
		return model;
	}

	

	/**
	 * Returns the ListSelectionModel
	 * @return the ListSelectionModel
	 * @since 0.1
	 * @see JList
	 */
	public ListSelectionModel getSelectionModel() {
		return selectionModel;
	}

	/**
	 * Returns the selected Index from the ListSelectionModel
	 * @return the selected Index
	 * @see ListSelectionModel
	 * @since 0.1
	 */
	public int getSelectedIndex() {
		return  getSelectionModel().getMinSelectionIndex();
	}

	/**
	 * Returns the leadSelectionIndex from the ListSelectionModel
	 * @return the leadSelectionIndex
	 * @see ListSelectionModel
	 * @since 0.1
	 */
	public int getLeadSelectionIndex() {
		return getSelectionModel().getLeadSelectionIndex();
	}

	/**
	 * Returns the UI-Class for this JComponent. This is always a GridUI
	 * @return the UIClass
	 * @since 0.1
	 */
	public GridUI getUI() {
		return (GridUI) ui;
	}

	public void updateUI() {
		setUI(new MacOsGridUI());
		cellRendererManager.updateRendererUI();
	}

	/**
	 * Sets the rendering dimension for all elements in the grid. The width and height of each element is equals the dimenison.
	 * Fires <code>PropertyChangeEvent</code> with the <code>fixedCellDimension</code> propertyname
	 * @param dimension the new dimension for this Grid
	 * @since 0.1
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
	 * Fires <code>PropertyChangeEvent</code> with the <code>verticalMargin</code> propertyname
	 * @param verticalMargin the vertical margin for this Grid
	 * @since 0.1
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
	 * Fires <code>PropertyChangeEvent</code> with the <code>horizonztalMargin</code> propertyname
	 * @param horizonztalMargin the horizonztal margin for this Grid
	 * @since 0.1
	 */
	public void setHorizonztalMargin(int horizonztalMargin) {
		int oldValue = horizonztalMargin;
		this.horizonztalMargin = horizonztalMargin;
		firePropertyChange("horizonztalMargin", oldValue, horizonztalMargin);
		revalidate();
		repaint();
	}

	/**
	 * Returns the Dimension of a cell. Each cell in the Grid has a width and height equals the fixedCellDimension
	 * @return the fixedCellDimension
	 * @since 0.1
	 */
	public int getFixedCellDimension() {
		return fixedCellDimension;
	}

	/**
	 * Returns the horizonztalMargin. The horizonztalMargin is the horizontal space between two cells
	 * @return the horizonztalMargin
	 * @since 0.1
	 */
	public int getHorizonztalMargin() {
		return horizonztalMargin;
	}

	/**
	 * Returns the verticalMargin. The verticalMargin is the vertical space between two cells
	 * @return the verticalMargin
	 * @since 0.1
	 */
	public int getVerticalMargin() {
		return verticalMargin;
	}

	public String getUIClassID() {
		return uiClassID;
	}

	/**
	 * Setter for the UIClass
	 * @param ui the new UI
	 * @since 0.1
	 */
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

	/**
	 * Getter for the selectionForeground. Each selected Cell paints the foreground in this Color. That means the foreground-Property in the renderer is set to the selectionForeground.
	 * 
	 * @return the selectionForeground
	 * @since 0.1
	 */
	public Color getSelectionForeground() {
		return selectionForeground;
	}

	/**
	 *  Setter for the selectionForeground. Each selected Cell paints the foreground in this Color. That means the foreground-Property in the renderer is set to the selectionForeground.
	 *  Fires <code>PropertyChangeEvent</code> with the <code>selectionForeground</code> propertyname
	 * @param selectionForeground the new selctionForeground
	 * @since 0.1
	 */
	public void setSelectionForeground(Color selectionForeground) {
		Color oldValue = this.selectionForeground;
		this.selectionForeground = selectionForeground;
		firePropertyChange("selectionForeground", oldValue, selectionForeground);
		repaint();
	}

	/**
	 *  Setter for the selectionBorderColor. The UIClass can use this for borderpainting.
	 *  Fires <code>PropertyChangeEvent</code> with the <code>selectionBorderColor</code> propertyname
	 * @param selectionForeground the new selctionForeground
	 * @since 0.1
	 */
	public void setSelectionBorderColor(Color selectionBorderColor) {
		Color oldValue = this.selectionBorderColor;
		this.selectionBorderColor = selectionBorderColor;
		firePropertyChange("selectionBorderColor", oldValue,
				selectionBorderColor);
		repaint();
	}

	/**
	 * Getter for the selectionBorderColor. The UIClass can use this for borderpainting.
	 * @return the selectionBorderColor
	 * @since 0.1
	 */
	public Color getSelectionBorderColor() {
		return selectionBorderColor;
	}

	/**
	 * Getter for the selectionBackground. Each selected Cell paints the background in this Color. That means the background-Property in the renderer is set to the selectionbackground.
	 * @return the selectionBackground
	 * @since 0.1
	 */
	public Color getSelectionBackground() {
		return selectionBackground;
	}

	/**
	 * Setter for the selectionBackground. Each selected Cell paints the background in this Color. That means the background-Property in the renderer is set to the selectionbackground.
	 * Fires <code>PropertyChangeEvent</code> with the <code>selectionBackground</code> propertyname
	 * @param selectionBackground the new selectionBackground
	 * @since 0.1
	 */
	public void setSelectionBackground(Color selectionBackground) {
		Color oldValue = this.selectionBackground;
		this.selectionBackground = selectionBackground;
		firePropertyChange("selectionBackground", oldValue, selectionBackground);
		repaint();
	}

	/**
	 * Getter for the default cellbackground. Each Cell paints the background in this Color. That means the background-Property in the renderer is set to the cellBackground.
	 * @return the default cellbackground
	 * @since 0.1
	 */
	public Color getCellBackground() {
		return cellBackground;
	}

	/**
	 * Setter for the default cellbackground. Each Cell paints the background in this Color. That means the background-Property in the renderer is set to the cellBackground.
	 * Fires <code>PropertyChangeEvent</code> with the <code>cellBackground</code> propertyname
	 * @param cellBackground the new default cellbackground
	 * @since 0.1
	 */
	public void setCellBackground(Color cellBackground) {
		Color oldValue = this.cellBackground;
		this.cellBackground = cellBackground;
		firePropertyChange("cellBackground", oldValue, cellBackground);
		repaint();
	}

	/**
	 * Sets the index of the selected cell
	 * @param index the index of the selected cell
	 * @since 0.1
	 */
	public void setSelectedIndex(int index) {
		if (index >= getModel().getSize()) {
			return;
		}
		getSelectionModel().setSelectionInterval(index, index);
	}


	/**
	 * Returns the bounds inside the grid for the cell at index
	 * @param index the index of the cell
	 * @return the cellbounds
	 * @since 0.1
	 */
	public Rectangle getCellBounds(int index) {
		return getUI().getCellBounds(index);
	}
	
	/**
	 * Returns the index of the cell at the given point. Returns -1 if no cell is at this point
	 * @param point the pint in the grid
	 * @return the index of the cell at the point
	 * @see de.jgrid.ui.GridUI
	 * @since 0.1
	 */
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
					Component renderer = getCellRenderer(index)
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

	 public GridCellRenderer getCellRenderer(int index) {
		 //TODO: Vererbung fehlt hier všllig!!!
		 return cellRendererManager.getRendererForClass(getModel().getElementAt(index).getClass());
	 }
	
	/**
	 * Getter for the horizontal alignment. <code>LEFT</code> / <code>CENTER</code> / <code>RIGHT</code> / <code>LEADING</code> & <code>TRAILING</code> allowed
	 * @return the horizontal alignment
	 * @since 0.1
	 */
	public int getHorizontalAlignment() {
		return horizontalAlignment;
	}

	/**
	 * Setter for the horizontal alignment. <code>LEFT</code> / <code>CENTER</code> / <code>RIGHT</code> / <code>LEADING</code> & <code>TRAILING</code> allowed
	 * Fires <code>PropertyChangeEvent</code> with the <code>horizontalAlignment</code> propertyname
	 * @param alignment the new horizontal alignment
	 * @since 0.1
	 * @exception IllegalArgumentException if <code>alignment</code> not <code>LEFT</code> / <code>CENTER</code> / <code>RIGHT</code> / <code>LEADING</code> & <code>TRAILING</code>
	 */
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

	@Override
	public void contentsChanged(ListDataEvent e) {
		resizeAndRepaint();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		resizeAndRepaint();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		resizeAndRepaint();
	}

	@Override
	public void sortingChanged(ListSorterEvent e) {
		resizeAndRepaint();
	}
}