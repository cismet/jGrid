package de.guigarage.demos.tunes.views.controls;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import de.guigarage.demos.tunes.util.TunesUtilities;

public abstract class ControlButton extends JButton {

	private static final long serialVersionUID = 1L;

	private BufferedImage icon;
	
	public ControlButton(String iconPath, String tooltip) {
		setToolTipText(tooltip);
		setOpaque(false);
		setBorder(null);
		try {
			icon = ImageIO.read(TunesUtilities.load(iconPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(icon.getWidth(), icon.getHeight());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(icon, 0, 0, null);
	}
}
