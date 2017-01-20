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

/**
 * Works like the <code>ListCellRenderer</code> Identifies components that can be used to paint the cells in a JGrid.
 *
 * @author   hendrikebbers
 * @version  0.1
 * @see      ListCellRenderer
 * @see      JGrid
 * @since    0.1
 */
public interface GridCellRenderer {

    //~ Methods ----------------------------------------------------------------

    /**
     * Returns a component for rendering / painting the cell with the given index & properties.
     *
     * @param   grid          The JGrid where the renderer is painting
     * @param   value         The value that will be rendered by thy returning Component
     * @param   index         The cell index in the JGrid
     * @param   isSelected    true if the cell is selected
     * @param   cellHasFocus  true if the cell has focus
     *
     * @return  A Component for paint the cell in the JGrid
     */
    Component getGridCellRendererComponent(JGrid grid,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus);
}
