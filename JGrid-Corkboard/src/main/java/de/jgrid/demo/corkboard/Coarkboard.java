package de.jgrid.demo.corkboard;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.guigarage.jgrid.JGrid;

import de.jgrid.demo.util.ImageUtilities;
import de.jgrid.demo.util.UrlLoader;

public class Coarkboard extends JFrame {

	private static final long serialVersionUID = 1L;

	private BufferedImage background;
	
	public Coarkboard() {
		setTitle("Coarkboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			background = ImageUtilities.createCompatibleImage(ImageIO.read(UrlLoader.getInstance().load("/de/jgrid/demo/corkboard/cork.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DefaultListModel model = new DefaultListModel();
		
		model.addElement("Ich bin eine Notiz!");
		model.addElement("sadadda");
		model.addElement("sdds");
		model.addElement("sfdfdds");
		model.addElement("sdffdss");
		model.addElement(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
		
		JGrid grid = new JGrid(model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				int x = 0;
				int y = 0;
				while(x < getWidth()) {
					while(y < getHeight()) {
						g.drawImage(background, x, y, null);
						y = y + background.getHeight();
					}
					y = 0;
					x = x + background.getWidth();
				}
				super.paintComponent(g);
			}
		};
		grid.setOpaque(false);
		grid.setFixedCellDimension(300);
		grid.setHorizonztalMargin(4);
		grid.setVerticalMargin(4);
		grid.setHorizontalAlignment(SwingConstants.LEFT);
		grid.getCellRendererManager().addRendererMapping(String.class, new PostItRenderer());
		grid.getCellRendererManager().addRendererMapping(BufferedImage.class, new PolaroidRenderer());

		JScrollPane scrollPane = new JScrollPane(grid);
		scrollPane.setBorder(null);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(800, 600);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				new Coarkboard().setVisible(true);
			}
		});
	}
}
