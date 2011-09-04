package de.guigarage.demos.tunes.views.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.guigarage.demos.tunes.TunesApp;

public class ListViewButton extends ControlButton {

	private static final long serialVersionUID = 1L;

	public ListViewButton(final TunesApp app) {
		super("de/guigarage/demos/tunes/icon/list.png", "list view");
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				app.showListView();
			}
		});
	}
}
