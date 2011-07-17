package de.jgrid.selection;

import java.awt.Component;

import javax.swing.JLabel;

import de.jgrid.JGrid;
import de.jgrid.renderer.GridCellRenderer;

public class SelectionDemoRenderer extends JLabel implements GridCellRenderer {

	private static final long serialVersionUID = 1L;

	public SelectionDemoRenderer() {
		setHorizontalTextPosition(CENTER);
		setVerticalTextPosition(CENTER);
	}
	
	@Override
	public Component getGridCellRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setText("");
		if(isSelected) {
			if(grid.getSelectionModel().getLeadSelectionIndex() == index) {
				setText(getText() + "L");
			} if(grid.getSelectionModel().getAnchorSelectionIndex() == index) {
				setText(getText() + "A");
			} 
				setText(getText() + "S");
		}
		return this;
	}

}
