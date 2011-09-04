package de.guigarage.demos.tunes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.guigarage.demos.tunes.model.TunesAlbumListModel;
import de.guigarage.demos.tunes.views.TunesGridView;
import de.guigarage.demos.tunes.views.TunesListView;
import de.guigarage.demos.tunes.views.TunesToolbar;
import de.guigarage.demos.tunes.views.controls.GridViewButton;
import de.guigarage.demos.tunes.views.controls.ListViewButton;

public class TunesApp extends JFrame {

	private static final long serialVersionUID = 1L;

	private TunesAlbumListModel model;

	private ListSelectionModel selectionModel;
	
	private TunesListView listView;
	
	private TunesGridView gridView;
	
	public TunesApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("TunesApp");
		model = new TunesAlbumListModel();
		selectionModel = new DefaultListSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(new TunesToolbar(this), BorderLayout.NORTH);		
		getContentPane().add(getListView(), BorderLayout.CENTER);		
		
		JPanel bottomPanel = new JPanel() {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(0, 1, getWidth(), 1);
			}
		};
		bottomPanel.setBackground(new Color(34, 35, 37));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(new GridViewButton(this));
		bottomPanel.add(new ListViewButton(this));
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		setSize(300, 400);
	}
	
	public void showListView() {
		getContentPane().remove(getGridView());
		getContentPane().add(getListView(), BorderLayout.CENTER);	
		getListView().revalidate();
		getContentPane().repaint();
	}
	
	public void showGridView() {
		getContentPane().remove(getListView());
		getContentPane().add(getGridView(), BorderLayout.CENTER);	
		getGridView().revalidate();
		getContentPane().repaint();
	}
	
	public TunesListView getListView() {
		if(listView == null) {
			listView = new TunesListView(this);
		}
		return listView;
	}
	
	public TunesGridView getGridView() {
		if(gridView == null) {
			gridView = new TunesGridView(this);
		}
		return gridView;
	}
	
	public ListSelectionModel getSelectionModel() {
		return selectionModel;
	}
	
	public TunesAlbumListModel getModel() {
		return model;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				new TunesApp().setVisible(true);
			}
		});
	}
}
