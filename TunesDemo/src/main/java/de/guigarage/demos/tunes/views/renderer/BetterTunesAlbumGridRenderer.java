package de.guigarage.demos.tunes.views.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import de.guigarage.demos.tunes.model.TunesAlbum;
import de.guigarage.demos.tunes.util.TunesUtilities;
import de.jgrid.JGrid;
import de.jgrid.renderer.GridCellRenderer;

public class BetterTunesAlbumGridRenderer extends JComponent implements GridCellRenderer {

	private static final long serialVersionUID = 1L;

	private TunesAlbum album;
	
	private BufferedImage defaultCover;
	
	public BetterTunesAlbumGridRenderer() {
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
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD));
		Rectangle2D nameRect = g2.getFontMetrics().getStringBounds(album.getName(), g2);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN));
		Rectangle2D artistRect = g2.getFontMetrics().getStringBounds(album.getInterpreter(), g2);
		int coverDim = (int) Math.min(getWidth() - 2, getHeight() - 6 - nameRect.getHeight() - artistRect.getHeight() - 12);

		
		if(album != null && album.getCover() != null) {
			g2.drawImage(album.getCover(), (getWidth() - coverDim) / 2, 6, coverDim, coverDim, null);
		} else {
			g2.drawImage(defaultCover, (getWidth() - coverDim) / 2, 6, coverDim, coverDim, null);
		}
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD));
		g2.setColor(Color.WHITE);
		g2.drawString(album.getName(), (int) ((getWidth() - nameRect.getWidth()) / 2), (int) (6 + coverDim + nameRect.getHeight() + 6));
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN));
		g2.setColor(Color.LIGHT_GRAY);
		g2.drawString(album.getInterpreter(), (int) ((getWidth() - artistRect.getWidth()) / 2), (int) (6 + coverDim + nameRect.getHeight() + 2 + artistRect.getHeight() + 6));
		
		
		g2.dispose();
		
	}

}
