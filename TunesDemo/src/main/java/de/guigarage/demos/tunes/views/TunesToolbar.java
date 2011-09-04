package de.guigarage.demos.tunes.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.guigarage.demos.tunes.TunesApp;
import de.guigarage.demos.tunes.views.controls.DurationControl;
import de.guigarage.demos.tunes.views.controls.PauseButton;
import de.guigarage.demos.tunes.views.controls.PlayButton;
import de.guigarage.demos.tunes.views.controls.StopButton;

public class TunesToolbar extends JComponent {

	private static final long serialVersionUID = 1L;

	public TunesToolbar(TunesApp app) {
		setOpaque(false);

		JPanel durationPanel = new JPanel();
		durationPanel.setBorder(null);
		durationPanel.setOpaque(false);
		durationPanel.setLayout(new BorderLayout());
		durationPanel.add(new DurationControl(), BorderLayout.CENTER);
		//TODO: HACK
		durationPanel.add(new JComponent() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(5, 5);
			}
		}, BorderLayout.NORTH);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(null);
		controlPanel.setOpaque(false);
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		controlPanel.add(new PlayButton(app));
		controlPanel.add(new PauseButton(app));
		controlPanel.add(new StopButton(app));
		
		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.WEST);
		add(durationPanel, BorderLayout.CENTER);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		super.paintComponent(g);
		GradientPaint gradient = new GradientPaint(0, 0, new Color(22, 22, 22),
				0, getHeight(), new Color(80, 80, 80), true);
		g2.setPaint(gradient);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
	}
}
