package de.jgrid.demo.corkboard;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import com.guigarage.jgrid.JGrid;
import com.guigarage.jgrid.renderer.GridCellRenderer;

import de.jgrid.demo.util.ImageUtilities;
import de.jgrid.demo.util.UrlLoader;

public class PolaroidRenderer extends JComponent implements GridCellRenderer {

	private static final long serialVersionUID = 1L;

	private BufferedImage background;
	
	public PolaroidRenderer() {
		try {
			background = ImageUtilities.createCompatibleImage(ImageIO.read(UrlLoader.getInstance().load("/de/jgrid/demo/corkboard/polaroid.png")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Component getGridCellRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
			return this;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.drawImage(background, 0, 0, null);
		g2.dispose();
	}
}
