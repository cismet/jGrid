package de.jgrid.sort;

import javax.swing.ListModel;
import javax.swing.SortOrder;
import javax.swing.event.ListDataListener;

public interface ListSorter extends ListDataListener {
	
	public ListModel getModel();
	
	public void setModel(ListModel model);
	
	public SortOrder getSortOrder();
	
	public int getViewCellCount();
	
	public int convertCellIndexToModel(int index);
	
	public int convertCellIndexToView(int index);
	
	public void addListSorterListener(ListSorterListener l);
	
	public void removeListSorterListener(ListSorterListener l);
}
