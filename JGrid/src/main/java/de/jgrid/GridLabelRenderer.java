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

import java.awt.Component;

/**
 * Works like the <code>GridCellRenderer</code>
 * Identifies components that can be used to paint
 * the label of a cells in a JGrid
 * 
 * @author hendrikebbers
 * @see ListCellRenderer
 * @see JGrid
 * @see GridCellRenderer
 */
public interface GridLabelRenderer {

	/**
	 * @param grid The JGrid where the renderer is painting
	 * @param value The value that will be rendered by thy returning Component
	 * @param index The cell index in the JGrid 
	 * @param isSelected true if the cell is selected
	 * @param cellHasFocus true if the cell has focus
	 * @return A Component for paint the label of a cell in the JGrid
	 */
	public Component getGridLabelRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus);
}
