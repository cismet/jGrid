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
package de.loganmobile.grid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JGrid extends JComponent implements Scrollable, SwingConstants {

	// TODO: Drag&Drop, Sortierung

	private static final long serialVersionUID = 1L;
	private ListSelectionModel selectionModel;
	private ListModel dataModel;
	private GridCellRenderer defaultCellRenderer;
	private GridLabelRenderer defaultLabelRenderer;
	private ListSelectionListener selectionListener;
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

	public JGrid(ListModel dataModel) {
		if (dataModel == null) {
			throw new IllegalArgumentException("dataModel must be non null");
		}

		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.registerComponent(this);

		this.dataModel = dataModel;
		selectionModel = createSelectionModel();
		setAutoscrolls(true);
		setOpaque(true);
		updateUI();
		setDefaultCellRenderer(new DefaultGridRenderer());
	}

	protected ListSelectionModel createSelectionModel() {
		return new DefaultListSelectionModel();
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		if (selectionListener == null) {
			selectionListener = new ListSelectionHandler();
			getSelectionModel().addListSelectionListener(selectionListener);
		}

		listenerList.add(ListSelectionListener.class, listener);
	}

	private class ListSelectionHandler implements ListSelectionListener,
			Serializable {

		private static final long serialVersionUID = 1L;

		public void valueChanged(ListSelectionEvent e) {
			fireSelectionValueChanged(e.getFirstIndex(), e.getLastIndex(), e
					.getValueIsAdjusting());
		}
	}

	public GridLabelRenderer getDefaultLabelRenderer() {
		return defaultLabelRenderer;
	}

	public void setDefaultLabelRenderer(GridLabelRenderer defaultLabelRenderer) {
		GridLabelRenderer oldValue = this.defaultLabelRenderer;
		this.defaultLabelRenderer = defaultLabelRenderer;
		firePropertyChange("defaultLabelRenderer", oldValue,
				this.defaultLabelRenderer);
	}

	public int getCellLabelCap() {
		return cellLabelCap;
	}

	public boolean isLabelsVisible() {
		return labelsVisible;
	}

	public void setLabelsVisible(boolean labelsVisible) {
		boolean oldValue = this.labelsVisible;
		this.labelsVisible = labelsVisible;
		firePropertyChange("labelsVisible", oldValue, this.labelsVisible);
	}

	public ListModel getDataModel() {
		return dataModel;
	}

	public GridCellRenderer getDefaultCellRenderer() {
		return defaultCellRenderer;
	}

	public GridCellRenderer getCellRenderer() {
		return defaultCellRenderer;
	}

	protected void fireSelectionValueChanged(int firstIndex, int lastIndex,
			boolean isAdjusting) {
		Object[] listeners = listenerList.getListenerList();
		ListSelectionEvent e = null;

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ListSelectionListener.class) {
				if (e == null) {
					e = new ListSelectionEvent(this, firstIndex, lastIndex,
							isAdjusting);
				}
				((ListSelectionListener) listeners[i + 1]).valueChanged(e);
			}
		}
	}

	public int getSelectionMode() {
		return getSelectionModel().getSelectionMode();
	}

	public ListModel getModel() {
		return dataModel;
	}

	public void addSelectionInterval(int anchor, int lead) {
		getSelectionModel().addSelectionInterval(anchor, lead);
	}

	public void clearSelection() {
		getSelectionModel().clearSelection();
	}

	public ListSelectionModel getSelectionModel() {
		return selectionModel;
	}

	public int getSelectedIndex() {
		return getMinSelectionIndex();
	}

	public int getLeadSelectionIndex() {
		return getSelectionModel().getLeadSelectionIndex();
	}

	public int getMinSelectionIndex() {
		return getSelectionModel().getMinSelectionIndex();
	}

	public int getMaxSelectionIndex() {
		return getSelectionModel().getMaxSelectionIndex();
	}

	public GridUI getUI() {
		return (GridUI) ui;
	}

	public void updateUI() {
		setUI(new MacOsGridUI());
		GridCellRenderer renderer = getCellRenderer();
		if (renderer instanceof Component) {
			SwingUtilities.updateComponentTreeUI((Component) renderer);
		}
	}

	public void setFixedCellDimension(int dimension) {
		int oldValue = fixedCellDimension;
		fixedCellDimension = dimension;
		firePropertyChange("fixedCellDimension", oldValue, fixedCellDimension);
		revalidate();
		repaint();
	}

	public void setVerticalMargin(int verticalMargin) {
		int oldValue = verticalMargin;
		this.verticalMargin = verticalMargin;
		firePropertyChange("verticalMargin", oldValue, verticalMargin);
		revalidate();
		repaint();
	}

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

	protected int checkHorizontalKey(int key, String message) {
		if ((key == LEFT) || (key == CENTER) || (key == RIGHT)
				|| (key == LEADING) || (key == TRAILING)) {
			return key;
		} else {
			throw new IllegalArgumentException(message);
		}
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
			int index = getUI().getCellAt(p);
			
			if(index >= 0) {
				Rectangle cellBounds = getCellBounds(index);
				if(cellBounds != null && cellBounds.contains(p.x, p.y)) {
					Component rComponent = getDefaultCellRenderer()
					.getGridCellRendererComponent(
							this,
							getModel().getElementAt(index),
							index,
							getSelectionModel().isSelectedIndex(index),
							hasFocus()
									&& getSelectionModel()
											.getLeadSelectionIndex() == index);
					if (rComponent instanceof JComponent) {
						MouseEvent newEvent;

						p.translate(-cellBounds.x, -cellBounds.y);
						newEvent = new MouseEvent(rComponent, event.getID(), event
								.getWhen(), event.getModifiers(), p.x, p.y, event
								.getXOnScreen(), event.getYOnScreen(), event
								.getClickCount(), event.isPopupTrigger(),
								MouseEvent.NOBUTTON);

						String tip = ((JComponent) rComponent)
								.getToolTipText(newEvent);

						if (tip != null) {
							return tip;
						}
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
		if (alignment == horizontalAlignment)
			return;
		int oldValue = horizontalAlignment;
		horizontalAlignment = checkHorizontalKey(alignment,
				"horizontalAlignment");
		firePropertyChange("horizontalAlignment", oldValue, horizontalAlignment);
		repaint();
	}

}
