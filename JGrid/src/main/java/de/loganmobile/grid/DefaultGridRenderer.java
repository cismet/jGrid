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
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JLabel;

public class DefaultGridRenderer extends JLabel implements GridCellRenderer {

	private static final long serialVersionUID = 1L;

	public DefaultGridRenderer() {
		setHorizontalTextPosition(CENTER);
		setVerticalTextPosition(CENTER);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}

	@Override
	public Component getGridCellRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());

		Color bg = null;
		Color fg = null;

		if (isSelected) {
			setBackground(bg == null ? grid.getSelectionBackground() : bg);
			setForeground(fg == null ? grid.getSelectionForeground() : fg);
		} else {
			setBackground(grid.getBackground());
			setForeground(grid.getForeground());
		}

		if (value instanceof Icon) {
			setIcon((Icon) value);
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
	 */
	@Override
	public void repaint(long tm, int x, int y, int width, int height) {
	}

	/**
	 * Overridden for performance reasons.
	 */
	@Override
	public void repaint(Rectangle r) {
	}

	/**
	 * Overridden for performance reasons.
	 */
	@Override
	public void firePropertyChange(String propertyName, byte oldValue,
			byte newValue) {
	}

	/**
	 * Overridden for performance reasons.
	 */
	@Override
	public void firePropertyChange(String propertyName, char oldValue,
			char newValue) {
	}

	/**
	 * Overridden for performance reasons.
	 */
	@Override
	public void firePropertyChange(String propertyName, short oldValue,
			short newValue) {
	}

	/**
	 * Overridden for performance reasons.
	 */
	@Override
	public void firePropertyChange(String propertyName, int oldValue,
			int newValue) {
	}

	/**
	 * Overridden for performance reasons.
	 */
	@Override
	public void firePropertyChange(String propertyName, long oldValue,
			long newValue) {
	}

	/**
	 * Overridden for performance reasons.
	 */
	@Override
	public void firePropertyChange(String propertyName, float oldValue,
			float newValue) {
	}

	/**
	 * Overridden for performance reasons.
	 */
	@Override
	public void firePropertyChange(String propertyName, double oldValue,
			double newValue) {
	}

	/**
	 * Overridden for performance reasons.
	 */
	@Override
	public void firePropertyChange(String propertyName, boolean oldValue,
			boolean newValue) {
	}
}
