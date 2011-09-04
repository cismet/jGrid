package de.guigarage.demos.tunes.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.guigarage.demos.tunes.TunesApp;
import de.guigarage.demos.tunes.model.TunesAlbumListModel;
import de.guigarage.demos.tunes.util.TunesUtilities;

public class AbstractTunesDataView extends JPanel implements ListDataListener {

	private static final long serialVersionUID = 1L;

	private TunesApp app;
	
	private BufferedImage dropIcon;
	
	public AbstractTunesDataView(TunesApp app) {
		this.app = app;
		setLayout(new BorderLayout());
		setOpaque(false);
		app.getModel().addListDataListener(this);
		
		try {
			dropIcon = ImageIO.read(TunesUtilities.load("de/guigarage/demos/tunes/icon/drop.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Math.max(super.getPreferredSize().width, 164), Math.max(super.getPreferredSize().height, 200));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(app.getModel().getSize() <= 0) {
			int startX = Math.max(0, (getWidth() - 128) / 2);
			int startY = Math.max(32, (getHeight() - 128) / 2);
			g.drawImage(dropIcon, startX, startY, null);
		}
	}
	
	public ListSelectionModel getSelectionModel() {
		return app.getSelectionModel();
	}

	public TunesAlbumListModel getModel() {
		return app.getModel();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		repaint();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		repaint();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		repaint();
	}
}
