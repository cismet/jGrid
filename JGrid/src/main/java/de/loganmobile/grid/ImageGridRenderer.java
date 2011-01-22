package de.loganmobile.grid;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ImageGridRenderer extends JComponent implements GridCellRenderer {

	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	
	@Override
	public Component getGridCellRendererComponent(JGrid list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		image = null;
		if(value instanceof BufferedImage) {
			this.image = (BufferedImage) value;
		}
		return this;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
}
