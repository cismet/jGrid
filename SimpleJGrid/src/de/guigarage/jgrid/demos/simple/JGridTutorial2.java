package de.guigarage.jgrid.demos.simple;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jgrid.JGrid;
import de.jgrid.renderer.GridCellRenderer;

public class JGridTutorial2 extends JFrame {

	private static final long serialVersionUID = 1L;

	public JGridTutorial2() {
		setTitle("Simple JGrid Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JGrid grid = new JGrid();
		getContentPane().add(new JScrollPane(grid));
		
		DefaultListModel model = new DefaultListModel();
		
		Random r = new Random();
		
		for(int i=0; i < 100; i++) {
			model.addElement(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		}
		
		grid.setModel(model);
		
		grid.getCellRendererManager().setDefaultRenderer(new GridCellRenderer() {
			
			private JPanel panel = new JPanel();
			
			@Override
			public Component getGridCellRendererComponent(JGrid arg0, Object arg1,
					int arg2, boolean arg3, boolean arg4) {
				if(arg1 != null && arg1 instanceof Color) {
					panel.setBackground((Color) arg1);
				}
				return panel;
			}
		});
		
		grid.setFont(grid.getFont().deriveFont(40.0f));
		grid.setFixedCellDimension(56);
		grid.setHorizonztalMargin(10);
		grid.setVerticalMargin(4);
		grid.setHorizontalAlignment(SwingConstants.LEFT);
		grid.setBackground(Color.WHITE);
		grid.setSelectionBorderColor(Color.BLUE);
		grid.setSelectionBackground(Color.CYAN);
		grid.setCellBackground(Color.LIGHT_GRAY);
		
		final JSlider slider = new JSlider(32, 256);
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				grid.setFixedCellDimension(slider.getValue());
			}
		});
		
		getContentPane().add(slider, BorderLayout.SOUTH);
		
		setSize(400, 300);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new JGridTutorial2().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
