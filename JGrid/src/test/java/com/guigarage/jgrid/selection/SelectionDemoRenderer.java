package com.guigarage.jgrid.selection;

import java.awt.Component;

import com.guigarage.jgrid.JGrid;
import com.guigarage.jgrid.renderer.DefaultGridCellRenderer;


public class SelectionDemoRenderer extends DefaultGridCellRenderer {

	private static final long serialVersionUID = 1L;

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
