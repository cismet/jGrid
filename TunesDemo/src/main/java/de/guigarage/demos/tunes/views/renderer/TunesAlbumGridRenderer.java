package de.guigarage.demos.tunes.views.renderer;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import de.guigarage.demos.tunes.model.TunesAlbum;
import de.guigarage.demos.tunes.util.TunesUtilities;
import de.jgrid.JGrid;
import de.jgrid.renderer.GridCellRenderer;

public class TunesAlbumGridRenderer extends JComponent implements GridCellRenderer {

	private static final long serialVersionUID = 1L;

	private TunesAlbum album;
	
	private BufferedImage defaultCover;
	
	public TunesAlbumGridRenderer() {
		try {
			defaultCover = ImageIO.read(TunesUtilities.load("de/guigarage/demos/tunes/icon/default_cover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Component getGridCellRendererComponent(JGrid arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		if(arg1 != null && arg1 instanceof TunesAlbum) {
			album = (TunesAlbum) arg1;
		}
		return this;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(album != null && album.getCover() != null) {
			g2.drawImage(album.getCover(), 0, 0, getWidth(), getHeight(), null);
		} else {
			g2.drawImage(defaultCover, 0, 0, getWidth(), getHeight(), null);
		}
		g2.dispose();
	}

}
