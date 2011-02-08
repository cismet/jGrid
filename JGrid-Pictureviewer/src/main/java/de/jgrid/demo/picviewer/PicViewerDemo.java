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
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jgrid.JGrid;
import de.jgrid.demo.util.CoolProgressBarUI;
import de.jgrid.demo.util.UrlLoader;

public class PicViewerDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public PicViewerDemo() {
		setTitle("PictureViewer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		final DefaultListModel model = new DefaultListModel();

		final JFrame loadFrame = new JFrame("Loading Demo");
		loadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JProgressBar bar = new JProgressBar();
		bar.setForeground(Color.ORANGE);
		bar.setUI(new CoolProgressBarUI());
		loadFrame.getContentPane().setLayout(
				new FlowLayout(SwingUtilities.CENTER, 20, 20));
		loadFrame.getContentPane().add(bar);
		loadFrame.setResizable(false);
		loadFrame.pack();
		loadFrame.setLocationRelativeTo(null);
		loadFrame.setBackground(Color.DARK_GRAY);
		loadFrame.setVisible(true);

		final JGrid grid = new JGrid(model);
		grid.setDefaultCellRenderer(new PicViewerRenderer());
		grid.setFixedCellDimension(160);
		grid.addMouseMotionListener(new MouseAdapter() {

			int lastIndex = -1;

			@Override
			public void mouseMoved(MouseEvent e) {
				if (lastIndex >= 0) {
					Object o = grid.getModel().getElementAt(lastIndex);
					if (o instanceof PicViewerObject) {
						Rectangle r = grid.getCellBounds(lastIndex);
						if (r != null && !r.contains(e.getPoint())) {
							((PicViewerObject) o).setMarker(false);
							grid.repaint(r);
						}
					}
				}

				int index = grid.getCellAt(e.getPoint());
				if (index >= 0) {
					Object o = grid.getModel().getElementAt(index);
					if (o instanceof PicViewerObject) {
						Rectangle r = grid.getCellBounds(index);
						if (r != null) {
							((PicViewerObject) o).setFraction(((float) e
									.getPoint().x - (float) r.x)
									/ (float) r.width);
							((PicViewerObject) o).setMarker(true);
							lastIndex = index;
							grid.repaint(r);
						}
					}
				}

			}
		});

		JScrollPane scrollPane = new JScrollPane(grid);
		scrollPane.setBorder(null);

		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());

		final JSlider slider = new JSlider(128, 256, grid
				.getFixedCellDimension());
		slider.setValue(grid.getFixedCellDimension());
		slider.putClientProperty("JComponent.sizeVariant", "small");
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				grid.setFixedCellDimension(slider.getValue());
			}
		});
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new FlowLayout());
		try {
			sliderPanel.add(new JLabel(new ImageIcon(ImageIO.read(UrlLoader
					.getInstance().load(
							"/de/jgrid/demo/picviewer/dim_small.png")))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		sliderPanel.add(slider);
		try {
			sliderPanel.add(new JLabel(new ImageIcon(ImageIO.read(UrlLoader
					.getInstance().load(
							"/de/jgrid/demo/picviewer/dim_large.png")))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		controlPanel.add(sliderPanel, BorderLayout.EAST);
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
		getContentPane().setBackground(grid.getBackground());
		setSize(800, 600);
		setLocationRelativeTo(null);

		SwingWorker<Void, PicViewerObject> worker = new SwingWorker<Void, PicViewerObject>() {

			@Override
			protected Void doInBackground() throws Exception {
				int repeatCount = 5;
				for (int i = 1; i <= repeatCount; i++) {
					try {
						setProgress((i - 1) * 100 / repeatCount + 4);
						publish(new PicViewerObject(
								"/de/jgrid/demo/picviewer/coast/",
								(float) i / 5.0f));
						setProgress((i - 1) * 100 / repeatCount + 8);
						publish(new PicViewerObject(
								"/de/jgrid/demo/picviewer/dogs/",
								(float) i / 5.0f));
						setProgress((i - 1) * 100 / repeatCount + 12);
						publish(new PicViewerObject(
								"/de/jgrid/demo/picviewer/emotions/",
								(float) i / 5.0f));
						setProgress((i - 1) * 100 / repeatCount + 16);
						publish(new PicViewerObject(
								"/de/jgrid/demo/picviewer/flowers/",
								(float) i / 5.0f));
						setProgress((i - 1) * 100 / repeatCount + 20);
						publish(new PicViewerObject(
								"/de/jgrid/demo/picviewer/landscape/",
								(float) i / 5.0f));
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void process(List<PicViewerObject> chunks) {
				bar.setValue(getProgress());
				for (Iterator<PicViewerObject> iterator = chunks.iterator(); iterator
						.hasNext();) {
					model.addElement(iterator.next());
				}
			}

			@Override
			protected void done() {
				loadFrame.setVisible(false);
				setVisible(true);
			}
		};
		worker.execute();
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
				new PicViewerDemo();
			}
		});
	}
}
