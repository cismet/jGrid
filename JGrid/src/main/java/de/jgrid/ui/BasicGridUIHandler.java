package de.jgrid.ui;

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
		MouseListener, KeyListener, ListSelectionListener, ListDataListener {

	private JGrid grid;

	public BasicGridUIHandler(JGrid grid) {
		this.grid = grid;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO: Hier muss eigentlich nur ein Marker gesetzt werden, dass die
		// Bounds "dirty" sind. Dann werden sie erst neu berechnet, wenn sie
		// benötigt werden. Hierdurch wird die Methode zum Berechnen auch
		// private
		grid.getUI().updateCellBounds();
		// TODO: Alles unterhalb des neuen Index repainten
		grid.repaint();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO: Hier muss eigentlich nur ein Marker gesetzt werden, dass die
		// Bounds "dirty" sind. Dann werden sie erst neu berechnet, wenn sie
		// benötigt werden. Hierdurch wird die Methode zum Berechnen auch
		// private
		grid.getUI().updateCellBounds();
		// TODO: Alles unterhalb des gelöschten Index repainten
		grid.repaint();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		// TODO: Hier muss eigentlich nur ein Marker gesetzt werden, dass die
		// Bounds "dirty" sind. Dann werden sie erst neu berechnet, wenn sie
		// benötigt werden. Hierdurch wird die Methode zum Berechnen auch
		// private
		grid.getUI().updateCellBounds();
		// TODO: nur geänderte Indexes repainten
		grid.repaint();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO: Hier muss eigentlich nur ein Marker gesetzt werden, dass die
		// Bounds "dirty" sind. Dann werden sie erst neu berechnet, wenn sie
		// benötigt werden. Hierdurch wird die Methode zum Berechnen auch
		// private
		grid.getUI().updateCellBounds();
		// TODO: nur selektion repainten
		grid.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			int nextIndex = Math.max(0, grid.getSelectedIndex() - 1);
			grid.setSelectedIndex(nextIndex);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			int nextIndex = Math.min(grid.getModel().getSize(),
					grid.getSelectedIndex() + 1);
			grid.setSelectedIndex(nextIndex);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			int selectedIndex = grid.getSelectedIndex();
			int row = grid.getRowForIndex(selectedIndex);
			int column = grid.getColumnForIndex(selectedIndex);

			int nextIndex = Math.min(grid.getModel().getSize() - 1,
					grid.getIndexAt(row + 1, column));
			grid.setSelectedIndex(nextIndex);
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			int selectedIndex = grid.getSelectedIndex();
			int row = grid.getRowForIndex(selectedIndex);
			int column = grid.getColumnForIndex(selectedIndex);
			int nextIndex = Math.max(0, grid.getIndexAt(row - 1, column));
			grid.setSelectedIndex(nextIndex);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			int index = grid.getCellAt(e.getPoint());
			if (index >= 0) {
				grid.requestFocus();
			}
			if (e.isControlDown()) {
				// TODO: cmd on Mac
				grid.getSelectionModel().addSelectionInterval(index, index);
			} else if (e.isShiftDown()) {
				grid.getSelectionModel()
						.addSelectionInterval(
								grid.getSelectionModel()
										.getLeadSelectionIndex(), index);
			} else {
				grid.setSelectedIndex(index);
			}

		}
		// TODO: Wenn selection nicht sichtbar View anpassen (gehört das hier
		// hin???)
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
		// TODO: Hier muss eigentlich nur ein Marker gesetzt werden, dass die
		// Bounds "dirty" sind. Dann werden sie erst neu berechnet, wenn sie
		// benötigt werden. Hierdurch wird die Methode zum Berechnen auch
		// private
		grid.getUI().updateCellBounds();
	}

}
