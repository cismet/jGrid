package de.guigarage.demos.tunes.model;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

public class TunesAlbumTableModel extends AbstractTableModel implements ListDataListener {

	private static final long serialVersionUID = 1L;

	private TunesAlbumListModel listModel;
	
	public TunesAlbumTableModel(TunesAlbumListModel listModel) {
		this.listModel = listModel;
		listModel.addListDataListener(this);
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		fireTableRowsInserted(e.getIndex0(), e.getIndex1());
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		fireTableRowsUpdated(e.getIndex0(), e.getIndex1());
	}

	@Override
	public int getRowCount() {
		return listModel.getSize();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0) {
			return String.class;
		}
		if(columnIndex == 1) {
			return String.class;
		}
		if(columnIndex == 2) {
			return String.class;
		}
		return super.getColumnClass(columnIndex);
	}
	
	@Override
	public String getColumnName(int column) {
		if(column == 0) {
			return "Name";
		}
		if(column == 1) {
			return "Interpret";
		}
		if(column == 2) {
			return "Bewertung";
		}
		return super.getColumnName(column);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TunesAlbum album = listModel.get(rowIndex);
		if(columnIndex == 0) {
			return album.getName();
		}
		if(columnIndex == 1) {
			return album.getInterpreter();
		}
		if(columnIndex == 2) {
			return album.getRating();
		}
		return null;
	}
}
