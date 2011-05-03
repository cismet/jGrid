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

import javax.swing.JComponent;

/**
 * A default UI for the Grid. The UI is for MAC OS and the rendering looks like the grid in iPhoto.
 * @author Hendrik Ebbers
 * @since 0.1
 * @version 0.1
 */
public class MacOsGridUI extends GridUI {

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		grid.setBackground(new Color(103,103,103));
		grid.setOpaque(true);
		grid.setSelectionBorderColor(new Color(248,211,80));
		grid.setSelectionForeground(new Color(0,0,0));
		grid.setSelectionBackground(new Color(43,43,43));
		grid.setCellBackground(new Color(43,43,43));
	}
}
