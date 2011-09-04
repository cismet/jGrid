package de.guigarage.demos.tunes.views.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.guigarage.demos.tunes.TunesApp;

public class GridViewButton extends ControlButton {

	private static final long serialVersionUID = 1L;

	public GridViewButton(final TunesApp app) {
		super("de/guigarage/demos/tunes/icon/grid.png", "grid view");
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				app.showGridView();
			}
		});
	}
}
