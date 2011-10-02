package com.guigarage.jgrid.ui;

import javax.swing.ListSelectionModel;

public class GridSelectionUtilities {

	private GridSelectionUtilities() {}
	
	public static void checkForSurroundingSelections(ListSelectionModel selectionModel, int maxIndex) {
		if(selectionModel.getAnchorSelectionIndex() > selectionModel.getLeadSelectionIndex()) {
			int nextCheckIndex = selectionModel.getLeadSelectionIndex() - 1;
			while(selectionModel.isSelectedIndex(nextCheckIndex) && nextCheckIndex >= 0) {
				if (nextCheckIndex > 0) {
					selectionModel.addSelectionInterval(selectionModel.getLeadSelectionIndex(),
							nextCheckIndex);
				} else {
					selectionModel.setLeadSelectionIndex(0);
				}	
				nextCheckIndex--;
			}
			nextCheckIndex = selectionModel.getAnchorSelectionIndex() + 1;
			while (selectionModel.isSelectedIndex(nextCheckIndex) && nextCheckIndex <= maxIndex) {
				if (nextCheckIndex < maxIndex) {
					selectionModel.addSelectionInterval(nextCheckIndex, selectionModel.getLeadSelectionIndex());
					selectionModel.setAnchorSelectionIndex(nextCheckIndex);
				} else {
					selectionModel.setAnchorSelectionIndex(
							maxIndex);
				}
				nextCheckIndex++;
			}
		} else if(selectionModel.getAnchorSelectionIndex() < selectionModel.getLeadSelectionIndex()) {
			int nextCheckIndex = selectionModel.getLeadSelectionIndex() + 1;
			while (selectionModel.isSelectedIndex(nextCheckIndex) && nextCheckIndex <= maxIndex) {
				if (nextCheckIndex < maxIndex) {
					selectionModel.addSelectionInterval(selectionModel.getLeadSelectionIndex(),
							nextCheckIndex);
				} else {
					selectionModel.setLeadSelectionIndex(
							maxIndex);
				}
				nextCheckIndex++;
			}
			nextCheckIndex = selectionModel.getAnchorSelectionIndex() - 1;
			while (selectionModel.isSelectedIndex(nextCheckIndex) && nextCheckIndex >= 0) {
				if (nextCheckIndex > 0) {
					selectionModel.addSelectionInterval(nextCheckIndex, selectionModel.getLeadSelectionIndex());
					selectionModel.setAnchorSelectionIndex(nextCheckIndex);
				} else {
					selectionModel.setAnchorSelectionIndex(0);
				}	
				nextCheckIndex--;
			}
		}
	}
	
	/**
	 * Returns true is both the index and the leadIndex of the selectionModel
	 * are smaller or bigger than the ancestorIndex.
	 * 
	 * @param index
	 *            the index of interest
	 * @return true if index and leadIndex are on the same side
	 */
	public static boolean isOnSameSideFromAncestorAsLead(ListSelectionModel selectionModel, int index) {
		int ancestor = selectionModel.getAnchorSelectionIndex();
		int lead = selectionModel.getLeadSelectionIndex();
		if (lead <= ancestor && index <= ancestor) {
			return true;
		}
		if (lead >= ancestor && index >= ancestor) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true is the index is between the leadIndex and the ancestorIndex.
	 * 
	 * @param index
	 *            the index of interest
	 * @return true if  index is between the leadIndex and the ancestorIndex
	 */
	public static boolean isBetweenOrEqualsLeadAndAncestor(ListSelectionModel selectionModel, int index) {
		int ancestor = selectionModel.getAnchorSelectionIndex();
		int lead = selectionModel.getLeadSelectionIndex();
		if (ancestor <= index && index <= lead) {
			return true;
		}
		if (lead <= index && index <= ancestor) {
			return true;
		}
		return false;
	}
}
