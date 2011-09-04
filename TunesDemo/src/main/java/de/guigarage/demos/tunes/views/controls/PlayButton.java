package de.guigarage.demos.tunes.views.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;

import de.guigarage.demos.tunes.TunesApp;
import de.guigarage.demos.tunes.player.TunesPlayer;


public class PlayButton extends ControlButton {

	private static final long serialVersionUID = 1L;

	public PlayButton(final TunesApp app) {
		super("de/guigarage/demos/tunes/icon/start.png", "play");
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Executors.newSingleThreadExecutor().execute(new Runnable() {
					
					@Override
					public void run() {
					if (!TunesPlayer.getInstance().hasTrack()) {
						try {
							TunesPlayer
									.getInstance()
									.open(app.getModel().get(app.getSelectionModel().getMaxSelectionIndex()).getTracks().next());
						} catch (Exception exception) {
							exception.printStackTrace();
						} 
					}
					try {
						TunesPlayer.getInstance().play();
					} catch (Exception e) {
						e.printStackTrace();
					}
					}
				});
			}
		});
	}
}
