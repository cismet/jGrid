/*
 * Created on Jan 22, 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2011 Hendrik Ebbers
 */
package de.jgrid.demo.picviewer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jgrid.JGrid;
import de.jgrid.demo.util.UrlLoader;

public class PicViewerDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public PicViewerDemo() {
		setTitle("iPhoto");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		DefaultListModel model = new DefaultListModel();
		for (int i = 0; i < 10; i++) {
			try {
				model.addElement(new PicViewerObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/coast/").getFile())));
				model.addElement(new PicViewerObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/dogs/").getFile())));
				model.addElement(new PicViewerObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/emotions/").getFile())));
				model.addElement(new PicViewerObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/flowers/").getFile())));
				model.addElement(new PicViewerObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/landscape/").getFile())));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
		final JGrid grid = new JGrid(model);
		grid.setDefaultCellRenderer(new PicViewerRenderer());
		grid.setFixedCellDimension(256);
		grid.addMouseMotionListener(new MouseAdapter() {
			
			int lastIndex = -1;
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(lastIndex >= 0) {
					Object o = grid.getModel().getElementAt(lastIndex);
					if(o instanceof PicViewerObject) {
						Rectangle r = grid.getCellBounds(lastIndex);
						if(r != null && !r.contains(e.getPoint())) {
							((PicViewerObject) o).setMarker(false);
							grid.repaint(r);
						}
					}
				}
				
				int index = grid.getCellAt(e.getPoint());
				if(index >= 0) {
					Object o = grid.getModel().getElementAt(index);
					if(o instanceof PicViewerObject) {
						Rectangle r = grid.getCellBounds(index);
						if(r != null) {
							((PicViewerObject) o).setFraction(((float)e.getPoint().x - (float)r.x) / (float)r.width);
							((PicViewerObject) o).setMarker(true);
							lastIndex = index;
							grid.repaint(r);
						}
					}
				}
				
			}
		});
		
		getContentPane().add(new JScrollPane(grid), BorderLayout.CENTER);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		JButton creditsButton = new JButton("image credits");
		controlPanel.add(creditsButton, BorderLayout.WEST);
		
		final JSlider slider = new JSlider(128, 1024, grid.getFixedCellDimension());
		slider.putClientProperty( "JComponent.sizeVariant", "small" );
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				grid.setFixedCellDimension(slider.getValue());
			}
		});
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new FlowLayout());
		try {
			sliderPanel.add(new JLabel(new ImageIcon(ImageIO.read(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/dim_small.png")))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		sliderPanel.add(slider);
		try {
			sliderPanel.add(new JLabel(new ImageIcon(ImageIO.read(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/dim_large.png")))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		controlPanel.add(sliderPanel, BorderLayout.EAST);
		
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
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
					e.printStackTrace();
				}
				new PicViewerDemo().setVisible(true);
			}
		});
	}
}
