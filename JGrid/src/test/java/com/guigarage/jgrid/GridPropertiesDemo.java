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
package com.guigarage.jgrid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataListener;

import com.guigarage.jgrid.JGrid;


public class GridPropertiesDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	private JGrid grid;
	
	private JSlider sliderDimension;
	
	private JSlider sliderHorizontalMargin;
	
	private JSlider sliderVerticalMargin;
	
	private JComboBox comboHorizontalAlignment;
	
	private JPanel selectionPanelForegroundColor;
	
	private JPanel selectionPanelSelectionForegroundColor;
	
	private JPanel selectionPanelSelectionBackgroundColor;
	
	private JPanel selectionPanelSelectionBorderColor;
	
	private JPanel selectionPanelBackgroundColor;
	
	public GridPropertiesDemo() {
		setTitle("GridPropertiesDemo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		grid = new JGrid(new ListModel() {

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
		getContentPane().add(new JScrollPane(grid), BorderLayout.CENTER);
		
		JPanel optionPanel =new JPanel();
		optionPanel.setLayout(new GridLayout(10, 2));
		
		JFrame optionFrame = new JFrame("Properties");
		
		optionFrame.getContentPane().add(optionPanel, BorderLayout.SOUTH);
		
		optionPanel.add(new JLabel("CellDimension"));
		optionPanel.add(getSliderDimension());
		
		optionPanel.add(new JLabel("HorizontalMargin"));
		optionPanel.add(getSliderHorizontalMargin());
		
		optionPanel.add(new JLabel("VerticalMargin"));
		optionPanel.add(getSliderVerticalMargin());
		
		optionPanel.add(new JLabel("HorizontalAlignment"));
		optionPanel.add(getComboHorizontalAlignment());
		
		optionPanel.add(new JLabel("ForegroundColor"));
		optionPanel.add(getSelectionPanelForegroundColor());
		
		optionPanel.add(new JLabel("BackgroundColor"));
		optionPanel.add(getSelectionPanelBackgroundColor());
		
		optionPanel.add(new JLabel("SelectionForegroundColor"));
		optionPanel.add(getSelectionPanelSelectionForegroundColor());
		
		optionPanel.add(new JLabel("SelectionBackgroundColor"));
		optionPanel.add(getSelectionPanelSelectionBackgroundColor());
		
		optionPanel.add(new JLabel("SelectionBorderColor"));
		optionPanel.add(getSelectionPanelSelectionBorderColor());
		
		pack();
		optionFrame.pack();
		optionFrame.setVisible(true);
	}
	
	public JPanel getSelectionPanelSelectionForegroundColor() {
		if(selectionPanelSelectionForegroundColor == null) {
			selectionPanelSelectionForegroundColor = new JPanel();
			selectionPanelSelectionForegroundColor.setLayout(new FlowLayout());
			final JSlider sliderBackgroundColorR = new JSlider(0, 255);
			sliderBackgroundColorR.setValue(grid.getSelectionForeground().getRed());
			final JSlider sliderBackgroundColorG = new JSlider(0, 255);
			sliderBackgroundColorG.setValue(grid.getSelectionForeground().getGreen());
			final JSlider sliderBackgroundColorB = new JSlider(0, 255);
			sliderBackgroundColorB.setValue(grid.getSelectionForeground().getBlue());
			ChangeListener listenerBackgroundColor = new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					grid.setSelectionForeground(new Color(sliderBackgroundColorR.getValue(), sliderBackgroundColorG.getValue(), sliderBackgroundColorB.getValue()));
				}
			};
			sliderBackgroundColorR.addChangeListener(listenerBackgroundColor);
			sliderBackgroundColorG.addChangeListener(listenerBackgroundColor);
			sliderBackgroundColorB.addChangeListener(listenerBackgroundColor);
			selectionPanelSelectionForegroundColor.add(new JLabel("R:"));
			selectionPanelSelectionForegroundColor.add(sliderBackgroundColorR);
			selectionPanelSelectionForegroundColor.add(new JLabel("G:"));
			selectionPanelSelectionForegroundColor.add(sliderBackgroundColorG);
			selectionPanelSelectionForegroundColor.add(new JLabel("B:"));
			selectionPanelSelectionForegroundColor.add(sliderBackgroundColorB);
		}
		return selectionPanelSelectionForegroundColor;
	}
	
	public JPanel getSelectionPanelSelectionBackgroundColor() {
		if(selectionPanelSelectionBackgroundColor == null) {
			selectionPanelSelectionBackgroundColor = new JPanel();
			selectionPanelSelectionBackgroundColor.setLayout(new FlowLayout());
			final JSlider sliderBackgroundColorR = new JSlider(0, 255);
			sliderBackgroundColorR.setValue(grid.getSelectionBackground().getRed());
			final JSlider sliderBackgroundColorG = new JSlider(0, 255);
			sliderBackgroundColorG.setValue(grid.getSelectionBackground().getGreen());
			final JSlider sliderBackgroundColorB = new JSlider(0, 255);
			sliderBackgroundColorB.setValue(grid.getSelectionBackground().getBlue());
			ChangeListener listenerBackgroundColor = new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					grid.setSelectionBackground(new Color(sliderBackgroundColorR.getValue(), sliderBackgroundColorG.getValue(), sliderBackgroundColorB.getValue()));
				}
			};
			sliderBackgroundColorR.addChangeListener(listenerBackgroundColor);
			sliderBackgroundColorG.addChangeListener(listenerBackgroundColor);
			sliderBackgroundColorB.addChangeListener(listenerBackgroundColor);
			selectionPanelSelectionBackgroundColor.add(new JLabel("R:"));
			selectionPanelSelectionBackgroundColor.add(sliderBackgroundColorR);
			selectionPanelSelectionBackgroundColor.add(new JLabel("G:"));
			selectionPanelSelectionBackgroundColor.add(sliderBackgroundColorG);
			selectionPanelSelectionBackgroundColor.add(new JLabel("B:"));
			selectionPanelSelectionBackgroundColor.add(sliderBackgroundColorB);
		}
		return selectionPanelSelectionBackgroundColor;
	}
	
	public JSlider getSliderVerticalMargin() {
		if(sliderVerticalMargin == null) {
			sliderVerticalMargin = new JSlider(0, 64);
			sliderVerticalMargin.setValue(grid.getVerticalMargin());
			sliderVerticalMargin.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					grid.setVerticalMargin(sliderVerticalMargin.getValue());
				}
			});
		}
		return sliderVerticalMargin;
	}
	
	public JSlider getSliderDimension() {
		if(sliderDimension == null) {
			sliderDimension = new JSlider(12, 512);
			sliderDimension.setValue(grid.getFixedCellDimension());
			sliderDimension.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					grid.setFixedCellDimension(sliderDimension.getValue());
				}
			});
		}
		return sliderDimension;
	}
	
	public JComboBox getComboHorizontalAlignment() {
		if(comboHorizontalAlignment == null) {
			String[] alignments = new String[]{"Center", "Right", "Left"};
			comboHorizontalAlignment = new JComboBox(alignments);
			comboHorizontalAlignment.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(comboHorizontalAlignment.getSelectedItem().equals("Center")) {
						grid.setHorizontalAlignment(SwingConstants.CENTER);
					} else if(comboHorizontalAlignment.getSelectedItem().equals("Right")) {
						grid.setHorizontalAlignment(SwingConstants.RIGHT);
					} else {
						grid.setHorizontalAlignment(SwingConstants.LEFT);
					}
				}
			});
		}
		return comboHorizontalAlignment;
	}
	
	public JSlider getSliderHorizontalMargin() {
		if(sliderHorizontalMargin == null) {
			sliderHorizontalMargin = new JSlider(0, 64);
			sliderHorizontalMargin.setValue(grid.getHorizonztalMargin());
			sliderHorizontalMargin.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					grid.setHorizonztalMargin(sliderHorizontalMargin.getValue());
				}
			});
		}
		return sliderHorizontalMargin;
	}
	
	public JPanel getSelectionPanelForegroundColor() {
		if(selectionPanelForegroundColor == null) {
			selectionPanelForegroundColor = new JPanel();
			selectionPanelForegroundColor.setLayout(new FlowLayout());
			final JSlider sliderForegroundColorR = new JSlider(0, 255);
			sliderForegroundColorR.setValue(grid.getForeground().getRed());
			final JSlider sliderForegroundColorG = new JSlider(0, 255);
			sliderForegroundColorG.setValue(grid.getForeground().getGreen());
			final JSlider sliderForegroundColorB = new JSlider(0, 255);
			sliderForegroundColorB.setValue(grid.getForeground().getBlue());
			ChangeListener listenerForegroundColor = new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					grid.setForeground(new Color(sliderForegroundColorR.getValue(), sliderForegroundColorG.getValue(), sliderForegroundColorB.getValue()));
				}
			};
			sliderForegroundColorR.addChangeListener(listenerForegroundColor);
			sliderForegroundColorG.addChangeListener(listenerForegroundColor);
			sliderForegroundColorB.addChangeListener(listenerForegroundColor);
			selectionPanelForegroundColor.add(new JLabel("R:"));
			selectionPanelForegroundColor.add(sliderForegroundColorR);
			selectionPanelForegroundColor.add(new JLabel("G:"));
			selectionPanelForegroundColor.add(sliderForegroundColorG);
			selectionPanelForegroundColor.add(new JLabel("B:"));
			selectionPanelForegroundColor.add(sliderForegroundColorB);
		}
		return selectionPanelForegroundColor;
	}
	
	public JPanel getSelectionPanelBackgroundColor() {
		if(selectionPanelBackgroundColor == null) {
			selectionPanelBackgroundColor = new JPanel();
			selectionPanelBackgroundColor.setLayout(new FlowLayout());
			final JSlider sliderBackgroundColorR = new JSlider(0, 255);
			sliderBackgroundColorR.setValue(grid.getBackground().getRed());
			final JSlider sliderBackgroundColorG = new JSlider(0, 255);
			sliderBackgroundColorG.setValue(grid.getBackground().getGreen());
			final JSlider sliderBackgroundColorB = new JSlider(0, 255);
			sliderBackgroundColorB.setValue(grid.getBackground().getBlue());
			ChangeListener listenerBackgroundColor = new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					grid.setBackground(new Color(sliderBackgroundColorR.getValue(), sliderBackgroundColorG.getValue(), sliderBackgroundColorB.getValue()));
				}
			};
			sliderBackgroundColorR.addChangeListener(listenerBackgroundColor);
			sliderBackgroundColorG.addChangeListener(listenerBackgroundColor);
			sliderBackgroundColorB.addChangeListener(listenerBackgroundColor);
			selectionPanelBackgroundColor.add(new JLabel("R:"));
			selectionPanelBackgroundColor.add(sliderBackgroundColorR);
			selectionPanelBackgroundColor.add(new JLabel("G:"));
			selectionPanelBackgroundColor.add(sliderBackgroundColorG);
			selectionPanelBackgroundColor.add(new JLabel("B:"));
			selectionPanelBackgroundColor.add(sliderBackgroundColorB);
		}
		return selectionPanelBackgroundColor;
	}
	
	public JPanel getSelectionPanelSelectionBorderColor() {
		if(selectionPanelSelectionBorderColor == null) {
			selectionPanelSelectionBorderColor = new JPanel();
			selectionPanelSelectionBorderColor.setLayout(new FlowLayout());
			final JSlider sliderBackgroundColorR = new JSlider(0, 255);
			sliderBackgroundColorR.setValue(grid.getSelectionBorderColor().getRed());
			final JSlider sliderBackgroundColorG = new JSlider(0, 255);
			sliderBackgroundColorG.setValue(grid.getSelectionBorderColor().getGreen());
			final JSlider sliderBackgroundColorB = new JSlider(0, 255);
			sliderBackgroundColorB.setValue(grid.getSelectionBorderColor().getBlue());
			ChangeListener listenerBackgroundColor = new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					grid.setSelectionBorderColor(new Color(sliderBackgroundColorR.getValue(), sliderBackgroundColorG.getValue(), sliderBackgroundColorB.getValue()));
				}
			};
			sliderBackgroundColorR.addChangeListener(listenerBackgroundColor);
			sliderBackgroundColorG.addChangeListener(listenerBackgroundColor);
			sliderBackgroundColorB.addChangeListener(listenerBackgroundColor);
			selectionPanelSelectionBorderColor.add(new JLabel("R:"));
			selectionPanelSelectionBorderColor.add(sliderBackgroundColorR);
			selectionPanelSelectionBorderColor.add(new JLabel("G:"));
			selectionPanelSelectionBorderColor.add(sliderBackgroundColorG);
			selectionPanelSelectionBorderColor.add(new JLabel("B:"));
			selectionPanelSelectionBorderColor.add(sliderBackgroundColorB);
		}
		return selectionPanelSelectionBorderColor;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new GridPropertiesDemo().setVisible(true);
			}
		});
	}
}
