package de.jgrid.sort;

import javax.swing.SortOrder;
import javax.swing.event.ListDataEvent;

public class DefaultListSorter extends AbstractListSorter {

	private SortOrder sortOrder;
	
	private int[] sortedIndexes;
	
	public DefaultListSorter() {
		sortOrder = SortOrder.UNSORTED;
		createUnsortedIndexes();
	}
	
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
		sort();
	}
	
	@Override
	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void sort() {
		if(sortOrder.equals(SortOrder.UNSORTED)) {
			createUnsortedIndexes();
		} else if(sortOrder.equals(SortOrder.ASCENDING)) {
			//TODO: indexliste sortieren
		} else {
			//TODO: indexliste sortieren
		}
		fireSortOrderChanged();
	}
	
	private void createUnsortedIndexes() {
		sortedIndexes = new int[getModel().getSize()];
		for (int i = 0; i < sortedIndexes.length; i++) {
			sortedIndexes[i] = i;
		}
	}
	
	@Override
	public int convertCellIndexToModel(int index) {
		return sortedIndexes[index];
	}

	@Override
	public int convertCellIndexToView(int index) {
		for (int i = 0; i < sortedIndexes.length; i++) {
			if(sortedIndexes[i] == index) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		sort();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		sort();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		sort();
	}

	@Override
	public int getViewCellCount() {
		return getModel().getSize();
	}
}
