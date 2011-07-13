package de.jgrid.eventproxies;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListSelectionProxy implements ListSelectionListener {

	private List<ListSelectionListener> listenerList;
	
	public void addListSelectionListener(ListSelectionListener l) {
		if(listenerList == null) {
			listenerList = new ArrayList<ListSelectionListener>();
		}
		listenerList.add(l);
	}
	
	public void removeListSelectionListener(ListSelectionListener l) {
		if(listenerList != null) {
			listenerList.add(l);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(listenerList != null) {
			for (ListSelectionListener listener: listenerList) {
				listener.valueChanged(e);
			}
		}
	}

}
