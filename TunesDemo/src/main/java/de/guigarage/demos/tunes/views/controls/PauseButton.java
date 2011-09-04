package de.guigarage.demos.tunes.views.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;

import de.guigarage.demos.tunes.TunesApp;
import de.guigarage.demos.tunes.player.TunesPlayer;

public class PauseButton extends ControlButton {

	private static final long serialVersionUID = 1L;

	public PauseButton(final TunesApp app) {
		super("de/guigarage/demos/tunes/icon/pause.png", "pause");

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Executors.newSingleThreadExecutor().execute(new Runnable() {

					@Override
					public void run() {
						TunesPlayer.getInstance().pause();
					}
				});
			}
		});
	}
}
