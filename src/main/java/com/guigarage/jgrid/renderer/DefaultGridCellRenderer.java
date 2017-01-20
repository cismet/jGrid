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
package com.guigarage.jgrid.renderer;

import com.guigarage.jgrid.JGrid;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * The default CellRenderer for JGrid. Works like DefaultListRenderer.
 *
 * @author   Hendrik Ebbers
 * @version  0.1
 * @see      DefaultListRenderer
 * @since    0.1
 */
public class DefaultGridCellRenderer extends JLabel implements GridCellRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final long serialVersionUID = 1L;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DefaultGridCellRenderer object.
     */
    public DefaultGridCellRenderer() {
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(CENTER);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Component getGridCellRendererComponent(final JGrid grid,
            final Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus) {
        if (isSelected) {
            setBackground(grid.getSelectionBackground());
            setForeground(grid.getSelectionForeground());
        } else {
            setBackground(grid.getBackground());
            setForeground(grid.getForeground());
        }

        if (value instanceof Icon) {
            setIcon((Icon)value);
            setText("");
        } else {
            setIcon(null);
            setText((value == null) ? "" : value.toString());
        }

        setEnabled(grid.isEnabled());
        setFont(grid.getFont());
        return this;
    }

    @Override
    public String getToolTipText() {
        return getText();
    }

    /**
     * Overridden for performance reasons.
     */
    @Override
    public void validate() {
    }

    /**
     * Overridden for performance reasons.
     */
    @Override
    public void invalidate() {
    }

    /**
     * Overridden for performance reasons.
     */
    @Override
    public void repaint() {
    }

    /**
     * Overridden for performance reasons.
     */
    @Override
    public void revalidate() {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  tm      DOCUMENT ME!
     * @param  x       DOCUMENT ME!
     * @param  y       DOCUMENT ME!
     * @param  width   DOCUMENT ME!
     * @param  height  DOCUMENT ME!
     */
    @Override
    public void repaint(final long tm, final int x, final int y, final int width, final int height) {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  r  DOCUMENT ME!
     */
    @Override
    public void repaint(final Rectangle r) {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  propertyName  DOCUMENT ME!
     * @param  oldValue      DOCUMENT ME!
     * @param  newValue      DOCUMENT ME!
     */
    @Override
    public void firePropertyChange(final String propertyName, final byte oldValue, final byte newValue) {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  propertyName  DOCUMENT ME!
     * @param  oldValue      DOCUMENT ME!
     * @param  newValue      DOCUMENT ME!
     */
    @Override
    public void firePropertyChange(final String propertyName, final char oldValue, final char newValue) {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  propertyName  DOCUMENT ME!
     * @param  oldValue      DOCUMENT ME!
     * @param  newValue      DOCUMENT ME!
     */
    @Override
    public void firePropertyChange(final String propertyName, final short oldValue, final short newValue) {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  propertyName  DOCUMENT ME!
     * @param  oldValue      DOCUMENT ME!
     * @param  newValue      DOCUMENT ME!
     */
    @Override
    public void firePropertyChange(final String propertyName, final int oldValue, final int newValue) {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  propertyName  DOCUMENT ME!
     * @param  oldValue      DOCUMENT ME!
     * @param  newValue      DOCUMENT ME!
     */
    @Override
    public void firePropertyChange(final String propertyName, final long oldValue, final long newValue) {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  propertyName  DOCUMENT ME!
     * @param  oldValue      DOCUMENT ME!
     * @param  newValue      DOCUMENT ME!
     */
    @Override
    public void firePropertyChange(final String propertyName, final float oldValue, final float newValue) {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  propertyName  DOCUMENT ME!
     * @param  oldValue      DOCUMENT ME!
     * @param  newValue      DOCUMENT ME!
     */
    @Override
    public void firePropertyChange(final String propertyName, final double oldValue, final double newValue) {
    }

    /**
     * Overridden for performance reasons.
     *
     * @param  propertyName  DOCUMENT ME!
     * @param  oldValue      DOCUMENT ME!
     * @param  newValue      DOCUMENT ME!
     */
    @Override
    public void firePropertyChange(final String propertyName, final boolean oldValue, final boolean newValue) {
    }
}
