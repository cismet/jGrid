package de.loganmobile.grid;

import java.awt.Color;

import javax.swing.JComponent;

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
