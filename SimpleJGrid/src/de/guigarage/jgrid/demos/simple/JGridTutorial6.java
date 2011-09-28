package de.guigarage.jgrid.demos.simple;

import java.awt.Color;
import java.awt.Component;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.jgrid.JGrid;
import de.jgrid.renderer.GridCellRenderer;

public class JGridTutorial6 extends JFrame {

	private static final long serialVersionUID = 1L;

	public JGridTutorial6() {
		setTitle("Simple JGrid Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JGrid grid = new JGrid();
		grid.setFixedCellDimension(56);
		grid.setHorizonztalMargin(4);
		grid.setVerticalMargin(4);
		grid.setCellBackground(Color.LIGHT_GRAY);
		grid.setSelectionBackground(Color.LIGHT_GRAY);
		grid.getCellRendererManager().setDefaultRenderer(new GridColorCellRenderer());
		
		JList list = new JList();
		list.setCellRenderer(new ListColorCellRenderer());
		
		DefaultListModel model = new DefaultListModel();
		Random random = new Random();
		for(int i=0; i <= 100; i++) {
			model.addElement(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		}
		grid.setModel(model);
		list.setModel(model);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("grid", new JScrollPane(grid));
		tabbedPane.addTab("list", new JScrollPane(list));
		getContentPane().add(tabbedPane);
		
		
		setSize(400, 300);
	}
	
	public class GridColorCellRenderer extends JPanel implements GridCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getGridCellRendererComponent(JGrid grid,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if(value != null && value instanceof Color) {
				this.setBackground((Color) value);
			}
			return this;
		}
	}
	
	public class ListColorCellRenderer extends JPanel implements ListCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			if(value != null && value instanceof Color) {
				this.setBackground((Color) value);
			}
			if(isSelected) {
				setBorder(BorderFactory.createLineBorder(Color.black));
			} else {
				setBorder(null);
			}
			return this;
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new JGridTutorial6().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
