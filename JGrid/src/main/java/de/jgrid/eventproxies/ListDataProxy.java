package de.jgrid.eventproxies;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ListDataProxy implements ListDataListener {

	private List<ListDataListener> listenerList;
	
	public void addListDataListener(ListDataListener l) {
		if(listenerList == null) {
			listenerList = new ArrayList<ListDataListener>();
		}
		listenerList.add(l);
	}
	
	public void removeListDataListener(ListDataListener l) {
		if(listenerList != null) {
			listenerList.add(l);
		}
	}
	
	@Override
	public void intervalAdded(ListDataEvent e) {
		if(listenerList != null) {
			for (ListDataListener listener: listenerList) {
				listener.intervalAdded(e);
			}
		}
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		if(listenerList != null) {
			for (ListDataListener listener: listenerList) {
				listener.intervalRemoved(e);
			}
		}
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		if(listenerList != null) {
			for (ListDataListener listener: listenerList) {
				listener.contentsChanged(e);
			}
		}
	}
}
