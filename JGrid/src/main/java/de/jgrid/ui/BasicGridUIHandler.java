/*
 * Created on July 20, 2011
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
package de.jgrid.ui;

import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.jgrid.JGrid;

public class BasicGridUIHandler implements PropertyChangeListener,
		MouseListener, KeyListener, ListSelectionListener, ListDataListener, ComponentListener {

	private JGrid grid;

	public BasicGridUIHandler(JGrid grid) {
		this.grid = grid;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		grid.getUI().markCellBoundsAsDirty();
		// TODO: Alles unterhalb des neuen Index repainten
		grid.repaint();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		grid.getUI().markCellBoundsAsDirty();
		// TODO: Alles unterhalb des gelöschten Index repainten
		grid.repaint();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		grid.getUI().markCellBoundsAsDirty();
		// TODO: nur geänderte Indexes repainten
		grid.repaint();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		grid.getUI().markCellBoundsAsDirty();
		// TODO: nur selektion repainten
		grid.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int ancestor = grid.getSelectionModel().getAnchorSelectionIndex();
		int lead = grid.getSelectionModel().getLeadSelectionIndex();
		
		if (e.getKeyCode() == KeyEvent.VK_A && isMenuShortcutKeyDown(e)) {
			grid.getSelectionModel().addSelectionInterval(0, grid.getModel().getSize() - 1);
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			int nextIndex = grid.getSelectionModel().getLeadSelectionIndex() - 1;
			if(e.isShiftDown()) {
				if(!isBetweenOrEqualsLeadAndAncestor(nextIndex)) {
					if(grid.getSelectionModel().isSelectedIndex(nextIndex)) {
						//Einen überspringen und den nächsten selektieren
						if(nextIndex > 0) {
							grid.getSelectionModel().addSelectionInterval(lead, nextIndex - 1);
						} else {
							grid.getSelectionModel().setLeadSelectionIndex(0);
						}
					} else {
						grid.getSelectionModel().addSelectionInterval(lead, nextIndex);
					}
					grid.getSelectionModel().setAnchorSelectionIndex(ancestor);
				} else {
					if(nextIndex >= 0) {
						grid.getSelectionModel().removeSelectionInterval(lead, lead);
						grid.getSelectionModel().setAnchorSelectionIndex(ancestor);
						grid.getSelectionModel().setLeadSelectionIndex(nextIndex);
					}
				}
			} else {
				if(nextIndex >= 0) {
					grid.setSelectedIndex(nextIndex);
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			int nextIndex = grid.getSelectionModel().getLeadSelectionIndex() + 1;
			if(e.isShiftDown()) {
				if(!isBetweenOrEqualsLeadAndAncestor(nextIndex)) {
					if(grid.getSelectionModel().isSelectedIndex(nextIndex)) {
						//Einen überspringen und den nächsten selektieren
						if(nextIndex + 1 < grid.getModel().getSize()) {
							grid.getSelectionModel().addSelectionInterval(lead, nextIndex + 1);
						} else {
							grid.getSelectionModel().setLeadSelectionIndex(grid.getModel().getSize() - 1);
						}
					} else {
						if(nextIndex < grid.getModel().getSize()) {
							grid.getSelectionModel().addSelectionInterval(lead, nextIndex);
						}
					}
					grid.getSelectionModel().setAnchorSelectionIndex(ancestor);
				} else {
					if(nextIndex < grid.getModel().getSize() ) {
						grid.getSelectionModel().removeSelectionInterval(lead, lead);
						grid.getSelectionModel().setAnchorSelectionIndex(ancestor);
						grid.getSelectionModel().setLeadSelectionIndex(nextIndex);
					}
				}
			} else {
				if(nextIndex < grid.getModel().getSize()) {
					grid.setSelectedIndex(nextIndex);
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			int selectedIndex = grid.getSelectionModel().getLeadSelectionIndex();
			int row = grid.getRowForIndex(selectedIndex);
			int column = grid.getColumnForIndex(selectedIndex);

			int nextIndex = grid.getIndexAt(row + 1, column);
			if(e.isShiftDown()) {
				//TODO:Noch nicht richtig
				grid.getSelectionModel().addSelectionInterval(grid.getSelectionModel()
						.getLeadSelectionIndex(), nextIndex);
			} else {
				grid.setSelectedIndex(Math.min(grid.getModel().getSize() - 1,
						nextIndex));
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			int selectedIndex = grid.getSelectionModel().getLeadSelectionIndex();
			int row = grid.getRowForIndex(selectedIndex);
			int column = grid.getColumnForIndex(selectedIndex);
			int nextIndex = grid.getIndexAt(row - 1, column);
			if(e.isShiftDown()) {
				//TODO: Noch nicht richtig
				grid.getSelectionModel().addSelectionInterval(grid.getSelectionModel()
						.getLeadSelectionIndex(), nextIndex);
			} else {
				grid.setSelectedIndex(Math.max(0, nextIndex));
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			int index = grid.getCellAt(e.getPoint());
			if (index >= 0) {
				grid.requestFocus();
			}
			if (isMenuShortcutKeyDown(e)) {
				if(grid.getSelectionModel().isSelectedIndex(index)) {
					grid.getSelectionModel().removeSelectionInterval(index, index);
					
				} else {
					grid.getSelectionModel().addSelectionInterval(index, index);
				}
			} else if (e.isShiftDown()) {
				int ancestor = grid.getSelectionModel().getAnchorSelectionIndex();
				int lead = grid.getSelectionModel().getLeadSelectionIndex();
				
				if(isBetweenOrEqualsLeadAndAncestor(index)) {
					grid.getSelectionModel().removeSelectionInterval(lead, index);
					
					grid.getSelectionModel().addSelectionInterval(index, index);
					grid.getSelectionModel().setLeadSelectionIndex(index);
				} else {
					if(!isOnSameSideFromAncestorAsLead(index)) {
						grid.getSelectionModel().removeSelectionInterval(ancestor, lead);
						grid.getSelectionModel().addSelectionInterval(ancestor, index);
					} else {
						grid.getSelectionModel().addSelectionInterval(lead, index);
					}
				}
				grid.getSelectionModel().setAnchorSelectionIndex(ancestor);
			} else {
				grid.setSelectedIndex(index);
			}

		}
		// TODO: Wenn selection nicht sichtbar View anpassen (gehört das hier
		// hin???)
	}

	private boolean isOnSameSideFromAncestorAsLead(int index) {
		int ancestor = grid.getSelectionModel().getAnchorSelectionIndex();
		int lead = grid.getSelectionModel().getLeadSelectionIndex();
		if(lead <= ancestor && index <= ancestor) {
			return true;
		}
		if(lead >= ancestor && index >= ancestor) {
			return true;
		}
		return false;
	}
	
	private boolean isBetweenOrEqualsLeadAndAncestor(int index) {
		int ancestor = grid.getSelectionModel().getAnchorSelectionIndex();
		int lead = grid.getSelectionModel().getLeadSelectionIndex();
		if(ancestor <= index && index <= lead) {
			return true;
		}
		if(lead <= index && index <= ancestor) {
			return true;
		}
		return false;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		grid.getUI().markCellBoundsAsDirty();
	}

	public boolean isMenuShortcutKeyDown(InputEvent event) {
        return (event.getModifiers() & 
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
    }

	@Override
	public void componentResized(ComponentEvent e) {
		grid.getUI().markCellBoundsAsDirty();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		grid.getUI().markCellBoundsAsDirty();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		grid.getUI().markCellBoundsAsDirty();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		grid.getUI().markCellBoundsAsDirty();
	}
}
