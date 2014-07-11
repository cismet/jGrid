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
package com.guigarage.jgrid.ui;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.plaf.ComponentUI;

/**
 * Look and feel interface for JGrid.
 *
 * @author   Hendrik Ebbers
 * @version  0.1
 * @since    0.3
 */
public abstract class GridUI extends ComponentUI {

    //~ Methods ----------------------------------------------------------------

    /**
     * Returns the indexes of cells that intersect with <code>rect</code>, or an empty array if none do.
     *
     * @param   rect  the area of interest
     *
     * @return  array of cell indexes intersected by <code>rect</code>, in ascending order
     */
    public abstract int[] getCellsIntersectedBy(Rectangle rect);

    /**
     * Returns the index of the cell that <code>point</code> lies in or -1 if <code>point</code> is not in a cell.
     *
     * @param   point  the location of interest
     *
     * @return  cellindex at the point
     */
    public abstract int getCellAt(Point point);

    /**
     * Returns the Bounds of the cell with <code>index.</code>
     *
     * @param   index  the model-index
     *
     * @return  Bounds of the cell at the given index
     */
    public abstract Rectangle getCellBounds(int index);

    /**
     * Returns the current count of columns in the grid.
     *
     * @return  count of columns in the grid
     */
    public abstract int getColumnCount();

    /**
     * Returns the model-index of the cell at <code>row</code> / <code>column.</code>
     *
     * @param   row     the row of the cell
     * @param   column  the column of the cell
     *
     * @return  model-index of the cell
     */
    public abstract int getIndexAt(int row, int column);

    /**
     * Returns the current count of rows in the grid.
     *
     * @return  count of rows in the grid
     */
    public abstract int getRowCount();

    /**
     * Returns the index of the row where <code>modelIndex</code> is in.
     *
     * @param   modelIndex  selectedIndex the model-index
     *
     * @return  index of the row
     */
    public abstract int getRowForIndex(int modelIndex);

    /**
     * Returns the index of the column where <code>modelIndex</code> is in.
     *
     * @param   modelIndex  selectedIndex the model-index
     *
     * @return  index of the column
     */
    public abstract int getColumnForIndex(int modelIndex);

    /**
     * Marks the bounds of all rendered cells as dirty. Next time the bounds are needed or the grid is rendered all
     * bounds will refreshed
     */
    public abstract void markCellBoundsAsDirty();
}
