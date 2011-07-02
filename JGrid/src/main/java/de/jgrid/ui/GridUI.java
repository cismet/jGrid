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

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.plaf.ComponentUI;

public abstract class GridUI extends ComponentUI {

	public abstract int getCellAt(Point point);

	public abstract Rectangle getCellBounds(int index);
	
	public abstract int getColumnCount();
	
	public abstract int getIndexAt(int row, int column);

	public abstract int getRowCount();

	public abstract int getRowForIndex(int selectedIndex);
	
	public abstract int getColumnForIndex(int selectedIndex);

}