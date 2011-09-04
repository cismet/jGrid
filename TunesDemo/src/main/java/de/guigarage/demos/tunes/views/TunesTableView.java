package de.guigarage.demos.tunes.views;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.guigarage.demos.tunes.TunesApp;
import de.guigarage.demos.tunes.model.TunesAlbumTableModel;

public class TunesTableView extends AbstractTunesDataView {

	private static final long serialVersionUID = 1L;

	JTable table;
	
	private JScrollPane scrollPane;
	
	public TunesTableView(TunesApp app) {
		super(app);
		add(getScrollPane());
	}
	
	public JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBorder(null);
		}
		return scrollPane;
	}
	
	public JTable getTable() {
		if(table == null) {
			table = new JTable(new TunesAlbumTableModel(getModel()));
			table.setSelectionModel(getSelectionModel());
		}
		return table;
	}
}
