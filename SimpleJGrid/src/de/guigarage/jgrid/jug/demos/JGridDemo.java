package de.guigarage.jgrid.jug.demos;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.jgrid.JGrid;

public class JGridDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public JGridDemo() {
		setTitle("Simple JGrid Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JGrid grid = new JGrid();
		
		DefaultListModel model = new DefaultListModel();
		for (int i = 0; i < 100; i++) {
			model.addElement(new Integer(i));
		}
		
		grid.setModel(model);
		grid.setSelectionForeground(Color.YELLOW);
		grid.setSelectionBackground(Color.BLUE);
		grid.setCellBackground(Color.RED);
		
//		grid.setFont(grid.getFont().deriveFont(40.0f));
		
		getContentPane().add(new JScrollPane(grid));
		
		setSize(400, 300);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new JGridDemo().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
