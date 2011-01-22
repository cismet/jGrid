package de.loganmobile.grid;

import java.awt.Component;

public interface GridCellRenderer {

	public Component getGridCellRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus);
}
