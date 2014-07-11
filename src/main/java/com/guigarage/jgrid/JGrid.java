/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
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
package com.guigarage.jgrid;

import com.guigarage.jgrid.eventproxies.ListDataProxy;
import com.guigarage.jgrid.eventproxies.ListSelectionProxy;
import com.guigarage.jgrid.renderer.GridCellRenderer;
import com.guigarage.jgrid.renderer.GridCellRendererManager;
import com.guigarage.jgrid.ui.BasicGridUI;
import com.guigarage.jgrid.ui.GridUI;
import com.guigarage.jgrid.ui.MacOsGridUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;

/**
 * A Component that displays a list of Elements in a grid. The Elements stored is a separate model, {@code ListModel}.
 * So you can use a {@code JList} parallel to to the JGrid
 *
 * @author   Hendrik Ebbers
 * @version  0.1
 * @see      JList
 * @since    0.1
 */
public class JGrid extends JComponent implements Scrollable, SwingConstants {

    //~ Static fields/initializers ---------------------------------------------

    private static final long serialVersionUID = 1L;

    private static final String uiClassID = "GridUI";

    //~ Instance fields --------------------------------------------------------

    private ListSelectionModel selectionModel;
    private ListModel model;
    private GridCellRendererManager cellRendererManager;
    private int fixedCellDimension = 128;
    private int horizonztalMargin = 16;
    private int verticalMargin = 16;
    private Color selectionForeground;
    private Color selectionBorderColor;
    private Color selectionBackground;
    private Color cellBackground;
    private int horizontalAlignment = CENTER;
    private ListSelectionProxy selectionProxy = new ListSelectionProxy();
    private ListDataProxy dataProxy = new ListDataProxy();
    private Rectangle selectionRectangle;
    private Point selectionRectangleAnchor;

    //~ Constructors -----------------------------------------------------------

    /**
     * Constructs a {@code JGrid} with a DefaultListModel.
     *
     * @since  0.1
     */
    public JGrid() {
        this(new DefaultListModel());
    }

    /**
     * Constructs a {@code JGrid} that displays elements from the specified, {@code non-null}, model. All {@code JGrid}
     * constructors must delegate to this one.
     *
     * @param      model  the model for the grid
     *
     * @exception  IllegalArgumentException  if the model is {@code null}
     *
     * @since      0.1
     */
    public JGrid(final ListModel model) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("dataModel must be non null");
        }

        final ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
        toolTipManager.registerComponent(this);

        setSelectionModel(createDefaultSelectionModel());
        setModel(model);
        setAutoscrolls(true);
        setOpaque(true);
        setCellRendererManager(new GridCellRendererManager());
        setUI(new MacOsGridUI());
        updateUI();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Adds a listener to the JGrid, to be notified each time a change to the selection occurs. This Method uses a
     * {@code ListSelectionProxy}. Every time the underlying {@code ListSelectionModel} changes all listeners will be
     * registered to the new model and deregistered from the old one. For adding listeneres to the model-scope (direct
     * to the model) use ListSelectionListener.addListSelectionListener
     *
     * @param  l  listener the {@code ListSelectionListener} to add
     *
     * @see    #removeListSelectionListener
     */
    public void addListSelectionListener(final ListSelectionListener l) {
        selectionProxy.addListSelectionListener(l);
    }

    /**
     * Removes a listener from the JGrid.
     *
     * @param  l  listener the {@code ListSelectionListener} to remove
     *
     * @see    #addListSelectionListener
     */
    public void removeListSelectionListener(final ListSelectionListener l) {
        selectionProxy.removeListSelectionListener(l);
    }

    /**
     * Adds a listener to the JGrid, to be notified each time a change to the data occurs. This Method uses a
     * {@code ListDataProxy}. Every time the underlying {@code ListModel} changes all listeners will be registered to
     * the new model and deregistered from the old one. For adding listeneres to the model-scope (direct to the model)
     * use ListModel.addListDataListener
     *
     * @param  l  listener the {@code ListSelectionListener} to add
     *
     * @see    #removeListDataListener
     */
    public void addListDataListener(final ListDataListener l) {
        dataProxy.addListDataListener(l);
    }

    /**
     * Removes a listener from the JGrid.
     *
     * @param  l  listener the {@code ListDataListener} to remove
     *
     * @see    #addListDataListener
     */
    public void removeListDataListener(final ListDataListener l) {
        dataProxy.addListDataListener(l);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (selectionRectangle != null) {
            final Graphics2D g2 = (Graphics2D)g;

            g2.setColor(this.getSelectionBackground());
            g2.setStroke(new BasicStroke(3));
            g2.draw(selectionRectangle);

            g2.setColor(this.getSelectionBorderColor());
            g2.setStroke(new BasicStroke(1));
            g2.draw(selectionRectangle);

            g2.setColor(new Color(
                    selectionBorderColor.getRed(),
                    selectionBorderColor.getGreen(),
                    selectionBorderColor.getBlue(),
                    128));
            g2.fill(selectionRectangle);
        }
    }

    /**
     * Sets the {@code ListSelectionModel} of the JGrid.
     *
     * @param   selectionModel  the new {@code ListSelectionModel}
     *
     * @throws  IllegalArgumentException  if the model is null
     *
     * @see     #getSelectionModel()
     */
    public void setSelectionModel(final ListSelectionModel selectionModel) {
        if (selectionModel == null) {
            throw new IllegalArgumentException(
                "selectionModel must be non null");
        }
        if (this.selectionModel != null) {
            this.selectionModel.removeListSelectionListener(selectionProxy);
        }
        this.selectionModel = selectionModel;
        this.selectionModel.addListSelectionListener(selectionProxy);
    }

    /**
     * Sets the {@code GridCellRendererManager} of the JGrid.
     *
     * @param   cellRendererManager  the new {@code GridCellRendererManager}
     *
     * @throws  IllegalArgumentException  if the cellRendererManager is null
     *
     * @see     GridCellRendererManager
     * @see     #getCellRendererManager()
     * @since   0.3
     */
    public void setCellRendererManager(final GridCellRendererManager cellRendererManager)
            throws IllegalArgumentException {
        if (cellRendererManager == null) {
            throw new IllegalArgumentException(
                "cellRendererManager must be non null");
        }

        final GridCellRendererManager oldManager = this.cellRendererManager;

        this.cellRendererManager = cellRendererManager;
        cellRendererManager.updateRendererUI();

        firePropertyChange("cellRendererManager", oldManager,
            this.cellRendererManager);

        revalidateAndRepaint();
    }

    /**
     * Return the {@code GridCellRendererManager} of the JGrid.
     *
     * @return  The {@code GridCellRendererManager} of the JGrid
     *
     * @see     GridCellRendererManager
     * @since   0.3
     */
    public GridCellRendererManager getCellRendererManager() {
        return cellRendererManager;
    }

    /**
     * Sets the model that represents values of the JGrid.
     *
     * @param   model  the new {@code ListModel}
     *
     * @throws  IllegalArgumentException  if the model is null
     *
     * @see     #getModel
     */
    public void setModel(final ListModel model) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("model must be non null");
        }
        final ListModel oldModel = this.model;
        if (oldModel != null) {
            oldModel.removeListDataListener(dataProxy);
        }
        this.model = model;
        this.model.addListDataListener(dataProxy);

        firePropertyChange("model", oldModel, this.model);
        selectionModel.clearSelection();
    }

    /**
     * Calls revalidate() and repaint().
     */
    protected void revalidateAndRepaint() {
        revalidate();
        repaint();
    }

    /**
     * Returns an default instance of {@code ListSelectionModel}; called during construction to initialize the grids
     * selectionModel. Normally this returns a {@code DefaultListSelectionModel}
     *
     * @return  a default ListSelectionModel
     *
     * @see     JList
     * @since   0.1
     */
    protected ListSelectionModel createDefaultSelectionModel() {
        return new DefaultListSelectionModel();
    }

    /**
     * Return the Model of the JGrid.
     *
     * @return  The Model of the JGrid
     *
     * @see     JList
     * @since   0.1
     */
    public ListModel getModel() {
        return model;
    }

    /**
     * Returns the ListSelectionModel.
     *
     * @return  the ListSelectionModel
     *
     * @see     JList
     * @since   0.1
     */
    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * Returns the selected Index from the ListSelectionModel.
     *
     * @return  the selected Index
     *
     * @see     ListSelectionModel
     * @since   0.1
     */
    public int getSelectedIndex() {
        return getSelectionModel().getMinSelectionIndex();
    }

    /**
     * Returns the leadSelectionIndex from the ListSelectionModel.
     *
     * @return  the leadSelectionIndex
     *
     * @see     ListSelectionModel
     * @since   0.1
     */
    public int getLeadSelectionIndex() {
        return getSelectionModel().getLeadSelectionIndex();
    }

    /**
     * Returns the UI-Class for this JComponent. This is always a GridUI
     *
     * @return  the UIClass
     *
     * @since   0.1
     */
    public GridUI getUI() {
        return (GridUI)ui;
    }

    @Override
    public void updateUI() {
        cellRendererManager.updateRendererUI();
    }

    /**
     * Sets the rendering dimension for all elements in the grid. The width and height of each element is equals the
     * dimenison. Fires <code>PropertyChangeEvent</code> with the <code>fixedCellDimension</code> propertyname
     *
     * @param  dimension  the new dimension for this Grid
     *
     * @since  0.1
     */
    public void setFixedCellDimension(final int dimension) {
        final int oldValue = fixedCellDimension;
        fixedCellDimension = dimension;
        firePropertyChange("fixedCellDimension", oldValue, fixedCellDimension);
        revalidate();
        repaint();
    }

    /**
     * Sets the vertical margin between all elements in the grid. Fires <code>PropertyChangeEvent</code> with the <code>
     * verticalMargin</code> propertyname
     *
     * @param  verticalMargin  the vertical margin for this Grid
     *
     * @since  0.1
     */
    public void setVerticalMargin(final int verticalMargin) {
        final int oldValue = verticalMargin;
        this.verticalMargin = verticalMargin;
        firePropertyChange("verticalMargin", oldValue, verticalMargin);
        revalidate();
        repaint();
    }

    /**
     * Sets the horizonztal margin between all elements in the grid. Fires <code>PropertyChangeEvent</code> with the
     * <code>horizonztalMargin</code> propertyname
     *
     * @param  horizonztalMargin  the horizonztal margin for this Grid
     *
     * @since  0.1
     */
    public void setHorizonztalMargin(final int horizonztalMargin) {
        final int oldValue = horizonztalMargin;
        this.horizonztalMargin = horizonztalMargin;
        firePropertyChange("horizonztalMargin", oldValue, horizonztalMargin);
        revalidate();
        repaint();
    }

    /**
     * Returns the Dimension of a cell. Each cell in the Grid has a width and height equals the fixedCellDimension
     *
     * @return  the fixedCellDimension
     *
     * @since   0.1
     */
    public int getFixedCellDimension() {
        return fixedCellDimension;
    }

    /**
     * Returns the horizonztalMargin. The horizonztalMargin is the horizontal space between two cells
     *
     * @return  the horizonztalMargin
     *
     * @since   0.1
     */
    public int getHorizonztalMargin() {
        return horizonztalMargin;
    }

    /**
     * Returns the verticalMargin. The verticalMargin is the vertical space between two cells
     *
     * @return  the verticalMargin
     *
     * @since   0.1
     */
    public int getVerticalMargin() {
        return verticalMargin;
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * Setter for the UIClass.
     *
     * @param  ui  the new UI
     *
     * @since  0.1
     */
    public void setUI(final BasicGridUI ui) {
        super.setUI(ui);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableBlockIncrement(final Rectangle visibleRect, final int orientation, final int direction) {
        return getVerticalMargin() + getVerticalMargin()
                    + getFixedCellDimension();
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        if (getParent() instanceof JViewport) {
            return (((JViewport)getParent()).getHeight() > getPreferredSize().height);
        }
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public int getScrollableUnitIncrement(final Rectangle visibleRect, final int orientation, final int direction) {
        return getVerticalMargin() + getVerticalMargin()
                    + getFixedCellDimension();
    }

    /**
     * Getter for the selectionForeground. Each selected Cell paints the foreground in this Color. That means the
     * foreground-Property in the renderer is set to the selectionForeground.
     *
     * @return  the selectionForeground
     *
     * @since   0.1
     */
    public Color getSelectionForeground() {
        return selectionForeground;
    }

    /**
     * Setter for the selectionForeground. Each selected Cell paints the foreground in this Color. That means the
     * foreground-Property in the renderer is set to the selectionForeground. Fires <code>PropertyChangeEvent</code>
     * with the <code>selectionForeground</code> propertyname
     *
     * @param  selectionForeground  the new selctionForeground
     *
     * @since  0.1
     */
    public void setSelectionForeground(final Color selectionForeground) {
        final Color oldValue = this.selectionForeground;
        this.selectionForeground = selectionForeground;
        firePropertyChange("selectionForeground", oldValue, selectionForeground);
        repaint();
    }

    /**
     * Setter for the selectionBorderColor. The UIClass can use this for borderpainting. Fires <code>
     * PropertyChangeEvent</code> with the <code>selectionBorderColor</code> propertyname
     *
     * @param  selectionBorderColor  DOCUMENT ME! the new selctionForeground
     *
     * @since  0.1
     */
    public void setSelectionBorderColor(final Color selectionBorderColor) {
        final Color oldValue = this.selectionBorderColor;
        this.selectionBorderColor = selectionBorderColor;
        firePropertyChange("selectionBorderColor", oldValue,
            selectionBorderColor);
        repaint();
    }

    /**
     * Getter for the selectionBorderColor. The UIClass can use this for borderpainting.
     *
     * @return  the selectionBorderColor
     *
     * @since   0.1
     */
    public Color getSelectionBorderColor() {
        return selectionBorderColor;
    }

    /**
     * Getter for the selectionBackground. Each selected Cell paints the background in this Color. That means the
     * background-Property in the renderer is set to the selectionbackground.
     *
     * @return  the selectionBackground
     *
     * @since   0.1
     */
    public Color getSelectionBackground() {
        return selectionBackground;
    }

    /**
     * Setter for the selectionBackground. Each selected Cell paints the background in this Color. That means the
     * background-Property in the renderer is set to the selectionbackground. Fires <code>PropertyChangeEvent</code>
     * with the <code>selectionBackground</code> propertyname
     *
     * @param  selectionBackground  the new selectionBackground
     *
     * @since  0.1
     */
    public void setSelectionBackground(final Color selectionBackground) {
        final Color oldValue = this.selectionBackground;
        this.selectionBackground = selectionBackground;
        firePropertyChange("selectionBackground", oldValue, selectionBackground);
        repaint();
    }

    /**
     * Getter for the default cellbackground. Each Cell paints the background in this Color. That means the
     * background-Property in the renderer is set to the cellBackground.
     *
     * @return  the default cellbackground
     *
     * @since   0.1
     */
    public Color getCellBackground() {
        return cellBackground;
    }

    /**
     * Setter for the default cellbackground. Each Cell paints the background in this Color. That means the
     * background-Property in the renderer is set to the cellBackground. Fires <code>PropertyChangeEvent</code> with the
     * <code>cellBackground</code> propertyname
     *
     * @param  cellBackground  the new default cellbackground
     *
     * @since  0.1
     */
    public void setCellBackground(final Color cellBackground) {
        final Color oldValue = this.cellBackground;
        this.cellBackground = cellBackground;
        firePropertyChange("cellBackground", oldValue, cellBackground);
        repaint();
    }

    /**
     * Sets the index of the selected cell.
     *
     * @param  index  the index of the selected cell
     *
     * @since  0.1
     */
    public void setSelectedIndex(final int index) {
        if (index >= getModel().getSize()) {
            return;
        }
        getSelectionModel().setSelectionInterval(index, index);
    }

    /**
     * Returns the bounds inside the grid for the cell at index.
     *
     * @param   index  the index of the cell
     *
     * @return  the cellbounds
     *
     * @since   0.1
     */
    public Rectangle getCellBounds(final int index) {
        return getUI().getCellBounds(index);
    }

    /**
     * Returns the index of the cell at the given point. Returns -1 if no cell is at this point
     *
     * @param   point  the pint in the grid
     *
     * @return  the index of the cell at the point
     *
     * @see     com.guigarage.jgrid.ui.BasicGridUI
     * @since   0.1
     */
    public int getCellAt(final Point point) {
        return getUI().getCellAt(point);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   rectangle  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int[] getCellsIntersectedBy(final Rectangle rectangle) {
        return getUI().getCellsIntersectedBy(rectangle);
    }

    @Override
    public String getToolTipText(final MouseEvent event) {
        if (event != null) {
            final Point p = event.getPoint();
            final int index = getCellAt(p);

            if (index >= 0) {
                final Rectangle cellBounds = getCellBounds(index);
                if ((cellBounds != null) && cellBounds.contains(p.x, p.y)) {
                    final Component renderer = getCellRenderer(index).getGridCellRendererComponent(
                            this,
                            getModel().getElementAt(index),
                            index,
                            getSelectionModel().isSelectedIndex(index),
                            hasFocus()
                                    && (getSelectionModel().getLeadSelectionIndex() == index));
                    if (renderer instanceof JComponent) {
                        return ((JComponent)renderer).getToolTipText(new MouseEvent(
                                    renderer,
                                    event.getID(),
                                    event.getWhen(),
                                    event.getModifiers(),
                                    p.x
                                            - cellBounds.x,
                                    p.y
                                            - cellBounds.y,
                                    event.getXOnScreen(),
                                    event.getYOnScreen(),
                                    event.getClickCount(),
                                    event.isPopupTrigger(),
                                    event.getButton()));
                    }
                }
            }
        }
        return super.getToolTipText();
    }

    /**
     * Returns the {@ GridCellRenderer} for a specific cell.
     *
     * @param   index  the index of the cell
     *
     * @return  the {@ GridCellRenderer}
     *
     * @see     JGrid#getCellRendererManager()
     * @see     #setCellRendererManager(GridCellRendererManager)
     * @see     GridCellRendererManager
     * @since   0.3
     */
    public GridCellRenderer getCellRenderer(final int index) {
        return cellRendererManager.getRendererForClass(getModel().getElementAt(
                    index).getClass());
    }

    /**
     * Getter for the horizontal alignment. <code>LEFT</code> / <code>CENTER</code> / <code>RIGHT</code> / <code>
     * LEADING</code> & <code>TRAILING</code> allowed
     *
     * @return  the horizontal alignment
     *
     * @since   0.1
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * Setter for the horizontal alignment. <code>LEFT</code> / <code>CENTER</code> / <code>RIGHT</code> / <code>
     * LEADING</code> & <code>TRAILING</code> allowed Fires <code>PropertyChangeEvent</code> with the <code>
     * horizontalAlignment</code> propertyname
     *
     * @param      alignment  the new horizontal alignment
     *
     * @exception  IllegalArgumentException  if <code>alignment</code> not <code>LEFT</code> / <code>CENTER</code> /
     *                                       <code>RIGHT</code> / <code>LEADING</code> & <code>TRAILING</code>
     *
     * @since      0.1
     */
    public void setHorizontalAlignment(final int alignment) {
        if ((alignment == LEFT) || (alignment == CENTER)
                    || (alignment == RIGHT)
                    || (alignment == LEADING)
                    || (alignment == TRAILING)) {
            if (alignment == horizontalAlignment) {
                return;
            }
            final int oldValue = horizontalAlignment;
            horizontalAlignment = alignment;
            firePropertyChange("horizontalAlignment", oldValue,
                horizontalAlignment);
            repaint();
        } else {
            throw new IllegalArgumentException("Illegal HorizontalAlignment: "
                        + alignment);
        }
    }

    /**
     * Returns the model-index of the cell at <code>row</code> / <code>column.</code>
     *
     * @param   row     the row of the cell
     * @param   column  the column of the cell
     *
     * @return  model-index of the cell
     */
    public int getIndexAt(final int row, final int column) {
        return getUI().getIndexAt(row, column);
    }

    /**
     * Returns the index of the column where <code>modelIndex</code> is in.
     *
     * @param   index  selectedIndex the model-index
     *
     * @return  index of the column
     */
    public int getColumnForIndex(final int index) {
        return getUI().getColumnForIndex(index);
    }

    /**
     * Returns the index of the row where <code>modelIndex</code> is in.
     *
     * @param   index  selectedIndex the model-index
     *
     * @return  index of the row
     */
    public int getRowForIndex(final int index) {
        return getUI().getRowForIndex(index);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Rectangle getSelectionRectangle() {
        return selectionRectangle;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  selectionRectangle  DOCUMENT ME!
     */
    public void setSelectionRectangle(final Rectangle selectionRectangle) {
        this.selectionRectangle = selectionRectangle;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Point getSelectionRectangleAnchor() {
        return selectionRectangleAnchor;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  selectionRectangleAnchor  DOCUMENT ME!
     */
    public void setSelectionRectangleAnchor(final Point selectionRectangleAnchor) {
        this.selectionRectangleAnchor = selectionRectangleAnchor;
    }

    /**
     * Set the defaultRenderer. If no renderer is registered for a specific class the <code>defaultRenderer</code> will
     * be used.
     *
     * @param  defaultRenderer  the new defaultRenderer
     */
    public void setDefaultRenderer(final GridCellRenderer defaultRenderer) {
        cellRendererManager.setDefaultRenderer(defaultRenderer);
    }

    /**
     * Returns the defaultRenderer. If no renderer is registered for a specific class the <code>defaultRenderer</code>
     * will be used.
     *
     * @return  the defaultRenderer
     */
    public GridCellRenderer getDefaultRenderer() {
        return cellRendererManager.getDefaultRenderer();
    }

    /**
     * Adds a renderer to the handler. The renderer is the default renderer for the cellClass <code>cls</code>
     *
     * @param  cls       set the renderer for this class
     * @param  renderer  the renderer for all instances of <code>cls</code>
     */
    public void addRendererMapping(final Class<?> cls, final GridCellRenderer renderer) {
        cellRendererManager.addRendererMapping(cls, renderer);
    }
}
