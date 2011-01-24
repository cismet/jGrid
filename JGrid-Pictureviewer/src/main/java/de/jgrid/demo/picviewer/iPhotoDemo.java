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

import de.jgrid.JGrid;
import de.jgrid.demo.util.UrlLoader;

public class iPhotoDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public iPhotoDemo() {
		setTitle("iPhoto");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		DefaultListModel model = new DefaultListModel();
		for (int i = 0; i < 10; i++) {
			try {
				model.addElement(new BufferedImageIPhotoObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/coast/").toURI())));
				model.addElement(new BufferedImageIPhotoObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/dogs/").toURI())));
				model.addElement(new BufferedImageIPhotoObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/emotions/").toURI())));
				model.addElement(new BufferedImageIPhotoObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/flowers/").toURI())));
				model.addElement(new BufferedImageIPhotoObject(new File(UrlLoader.getInstance().load("/de/jgrid/demo/picviewer/landscape/").toURI())));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
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
					e.printStackTrace();
				}
				new iPhotoDemo().setVisible(true);
			}
		});
	}
}
