package de.loganmobile.grid;

import java.awt.Component;

public interface GridLabelRenderer {

	public Component getGridLabelRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus);
}
