package de.guigarage.demos.tunes.views.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import de.guigarage.demos.tunes.player.TunesPlayer;

public class DurationControl extends JComponent {

	private static final long serialVersionUID = 1L;

	public DurationControl() {
		setOpaque(false);
		setBorder(null);
		Executors.newSingleThreadExecutor().execute(new Runnable() {

			@Override
			public void run() {
				while (true) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							repaint();
						}
					});
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public String getToolTipText() {
		if (TunesPlayer.getInstance().hasTrack()) {
			long position = TunesPlayer.getInstance().getMicrosecondPosition();
			long positionMinutes = TimeUnit.MINUTES.convert(position,
					TimeUnit.MICROSECONDS);
			long postionSeconds = TimeUnit.SECONDS.convert(position,
					TimeUnit.MICROSECONDS)
					- TimeUnit.SECONDS.convert(positionMinutes,
							TimeUnit.MINUTES);
			String positionString = String.format("%d:%d", positionMinutes,
					postionSeconds);

			long duration = TunesPlayer.getInstance().getTrack().getDuration();
			long durationMinutes = TimeUnit.MINUTES.convert(duration,
					TimeUnit.MICROSECONDS);
			long durationSeconds = TimeUnit.SECONDS.convert(duration,
					TimeUnit.MICROSECONDS)
					- TimeUnit.SECONDS.convert(durationMinutes,
							TimeUnit.MINUTES);
			String durationString = String.format("%d:%d", durationMinutes,
					durationSeconds);

			return positionString + " - " + durationString;
		} else {
			return "-";
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(128, 32);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();

		// BACK
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(8, 12, getWidth() - 16, 8);
		g2.setColor( new Color(30, 30, 30, 100));
		g2.drawRect(9, 13, getWidth() - 18, 7);

		// TOP
		if (TunesPlayer.getInstance().hasTrack()) {
			long position = TunesPlayer.getInstance().getMicrosecondPosition();
			long duration = TunesPlayer.getInstance().getTrack().getDuration();
			float width = ((float)getWidth() - 16.0f) * ((float)position / (float)duration);
			g2.setColor(new Color(43, 58, 88).brighter());
			
			GradientPaint gradient = new GradientPaint(1, 12, new Color(43, 58, 88).brighter().brighter(), 1, 20,
					new Color(21, 34, 58).brighter(), true);
			g2.setPaint(gradient);
			
			g2.fillRect(8, 12, (int) width, 8);
		}

		// BORDER
		g2.setColor(Color.white);
		g2.drawRect(8, 12, getWidth() - 16, 8);
		g2.dispose();
	}
}
