package de.jgrid.demo.corkboard;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JTextArea;

import de.jgrid.JGrid;
import de.jgrid.demo.util.ImageUtilities;
import de.jgrid.demo.util.UrlLoader;
import de.jgrid.renderer.GridCellRenderer;

public class PostItRenderer extends JComponent implements GridCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage background;
	
	private Font font;
	
	private JTextArea textArea;
	
	public PostItRenderer() {
		try {
			background = ImageUtilities.createCompatibleImage(ImageIO.read(UrlLoader.getInstance().load("/de/jgrid/demo/corkboard/postit.png")));
			InputStream fontIn = UrlLoader.getInstance().load("/de/jgrid/demo/corkboard/note.ttf").openStream();
			font = Font.createFont(Font.TRUETYPE_FONT, fontIn).deriveFont(30.0f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		textArea = new JTextArea();
		textArea.setBorder(null);
		textArea.setOpaque(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		setLayout(null);
		add(textArea);
		textArea.setBounds(45, 40, 210, 200);
		textArea.setFont(font);
	}
	
	public Component getGridCellRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		textArea.setText(value.toString());
		return this;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.drawImage(background, 0, 0, null);
	}
	
}
