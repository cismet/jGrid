package de.guigarage.demos.tunes.views.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.guigarage.demos.tunes.model.TunesAlbum;
import de.guigarage.demos.tunes.util.TunesUtilities;

public class TunesAlbumListRenderer extends JComponent implements
		ListCellRenderer {

	private static final long serialVersionUID = 1L;

	private TunesAlbum album;
	
	private boolean isSelected;
	
	private BufferedImage defaultCover;
	
	public TunesAlbumListRenderer() {
		try {
			defaultCover = ImageIO.read(TunesUtilities.load("de/guigarage/demos/tunes/icon/default_cover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if(value != null && value instanceof TunesAlbum) {
			this.album = (TunesAlbum) value;
		} else {
			this.album = null;
		}
		this.isSelected = isSelected;
		return this;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(isSelected) {
			GradientPaint gradient = new GradientPaint(0, 0, new Color(43, 58, 88).brighter(), 0, getHeight(),
					new Color(21, 34, 58), true);
			g2.setPaint(gradient);
		} else {
			GradientPaint gradient = new GradientPaint(0, 0, new Color(48, 49, 53), 0, getHeight(),
					new Color(34, 35, 37), true);
			g2.setPaint(gradient);
		}
		
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		//Cover
		if(album != null && album.getCover() != null) {
//			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(TunesUtilities.getFasterScaledInstance(album.getCover(), 44, 44, RenderingHints.VALUE_INTERPOLATION_BICUBIC, true), 0, 0, null);
		} else {
//			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(TunesUtilities.getFasterScaledInstance(defaultCover, 44, 44, RenderingHints.VALUE_INTERPOLATION_BICUBIC, true), 0, 0, null);
		}
//		g2.setColor(new Color(50, 52, 55, 150));
//		g2.drawLine(0, 0, 43, 0);
//		g2.drawLine(0, 43, 43, 43);
//		g2.drawLine(0, 0, 0, 43);
//		g2.drawLine(43, 0, 43, 43);
		
		g2.setColor(new Color(50, 52, 55, 200));
		g2.drawLine(0, 0, getWidth(), 0);
		
		g2.setColor(new Color(0, 0, 0, 140));
		g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
		
//		g2.setColor(new Color(5, 5, 5, 180));
//		g2.drawLine(0, 0, 0, getHeight());
//		g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
		
		g2.setColor(Color.white);
		g2.setFont(getFont().deriveFont(Font.BOLD));
		if(album != null) {
			g2.drawString(album.getName(), 50, 20);
		}
		g2.setColor(new Color(255, 255, 255, 155));
		if(album != null) {
			g2.drawString(album.getInterpreter(), 50, 34);
		}
		
		g2.dispose();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(256, 44);
	}
}
