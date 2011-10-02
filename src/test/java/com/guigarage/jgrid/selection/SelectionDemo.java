package com.guigarage.jgrid.selection;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataListener;

import com.guigarage.jgrid.JGrid;


public class SelectionDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public SelectionDemo() {
		setTitle("SelectionDemo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JGrid grid = new JGrid(new ListModel() {

			@Override
			public void removeListDataListener(ListDataListener l) {
			}

			@Override
			public int getSize() {
				return 128;
			}

			@Override
			public Object getElementAt(int index) {
				return "Test" + index;
			}

			@Override
			public void addListDataListener(ListDataListener l) {
			}
		});
		grid.getCellRendererManager().setDefaultRenderer(new SelectionDemoRenderer());
		grid.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		getContentPane().add(new JScrollPane(grid), BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		new SelectionDemo().setVisible(true);
	}
}
