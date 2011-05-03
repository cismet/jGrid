package de.jgrid.sort;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.RowSorterEvent;

public abstract class AbstractListSorter implements ListSorter {

	private List<ListSorterListener> listeners;

	private ListModel model;
	
	public AbstractListSorter() {
		listeners = new ArrayList<ListSorterListener>();
	}
	
	@Override
	public ListModel getModel() {
		return model;
	}
	
	@Override
	public void setModel(ListModel model) {
		this.model = model;
		sort();
	}
	
	public abstract void sort();
	
	@Override
	public void addListSorterListener(ListSorterListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListSorterListener(ListSorterListener l) {
		listeners.remove(l);
	}

    protected void fireSortOrderChanged() {
        fireRowSorterChanged(new ListSorterEvent(this));
    }

    protected void fireRowSorterChanged(int[] lastRowIndexToModel) {
        fireRowSorterChanged(new ListSorterEvent(this,
                RowSorterEvent.Type.SORTED, lastRowIndexToModel));
    }

    void fireRowSorterChanged(ListSorterEvent event) {
    	for (ListSorterListener listener : listeners) {
			listener.sortingChanged(event);
		}
    }
	
}
