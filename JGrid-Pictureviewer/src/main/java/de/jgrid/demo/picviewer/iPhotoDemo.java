package de.jgrid.demo.picviewer;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.loganmobile.grid.JGrid;

public class iPhotoDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public iPhotoDemo() {
		setTitle("iPhoto");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		DefaultListModel model = new DefaultListModel();
		for (int i = 0; i < 10; i++) {
			model.addElement(new BufferedImageIPhotoObject(new File("src/main/java/de/jgrid/demo/picviewer/coast/")));
			model.addElement(new BufferedImageIPhotoObject(new File("src/main/java/de/jgrid/demo/picviewer/dogs")));
			model.addElement(new BufferedImageIPhotoObject(new File("src/main/java/de/jgrid/demo/picviewer/emotions")));
			model.addElement(new BufferedImageIPhotoObject(new File("src/main/java/de/jgrid/demo/picviewer/flowers")));
			model.addElement(new BufferedImageIPhotoObject(new File("src/main/java/de/jgrid/demo/picviewer/landscape")));
		}
		
		final JGrid grid = new JGrid(model);
		grid.setDefaultCellRenderer(new IPhotoRenderer());
		grid.setFixedCellDimension(256);
		grid.addMouseMotionListener(new MouseAdapter() {
			
			int lastIndex = -1;
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(lastIndex >= 0) {
					Object o = grid.getModel().getElementAt(lastIndex);
					if(o instanceof IPhotoModelObject) {
						Rectangle r = grid.getCellBounds(lastIndex);
						if(r != null && !r.contains(e.getPoint())) {
							((BufferedImageIPhotoObject) o).setMarker(false);
							grid.repaint(r);
						}
					}
				}
				
				int index = grid.getCellAt(e.getPoint());
				if(index >= 0) {
					Object o = grid.getModel().getElementAt(index);
					if(o instanceof IPhotoModelObject) {
						Rectangle r = grid.getCellBounds(index);
						if(r != null) {
							((IPhotoModelObject) o).setFraction(((float)e.getPoint().x - (float)r.x) / (float)r.width);
							((BufferedImageIPhotoObject) o).setMarker(true);
							lastIndex = index;
							grid.repaint(r);
						}
					}
				}
				
			}
		});
		
		final JSlider slider = new JSlider(128, 1024, grid.getFixedCellDimension());
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				grid.setFixedCellDimension(slider.getValue());
			}
		});
		
		getContentPane().add(new JScrollPane(grid), BorderLayout.CENTER);
		getContentPane().add(slider, BorderLayout.SOUTH);
		pack();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new iPhotoDemo().setVisible(true);
			}
		});
	}
}
