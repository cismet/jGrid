/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package com.guigarage.jgrid.ui;

import javax.swing.ListSelectionModel;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class ListSelectionUtilities {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ListSelectionUtilities object.
     */
    private ListSelectionUtilities() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Updates the anchor and lead of the given SelectionModel. The complete selection of the model will bordered by
     * anchor and lead. Examples: If your list selection looks like "---L**A**---" (- not selected / A anchor / L lead /
     * * selected) the method will transform the selection to "---L****A---" If your list selection looks like
     * "---A**L**---" the method will transform the selection to "---A****L---" If your list selection looks like
     * "--A**--L**--" the method will transform the selection to "--A**--**L--" If your list selection looks like
     * "*-**L--*A*-*" the method will transform the selection to "L-***--***-A"
     *
     * @param  selectionModel  the SelectionModel being updated
     * @param  maxIndex        the max index of the data model
     */
    public static void refreshAnchorAndLead(final ListSelectionModel selectionModel, final int maxIndex) {
        if (selectionModel.getAnchorSelectionIndex() > selectionModel.getLeadSelectionIndex()) {
            int nextCheckIndex = selectionModel.getLeadSelectionIndex() - 1;
            while (selectionModel.isSelectedIndex(nextCheckIndex) && (nextCheckIndex >= 0)) {
                if (nextCheckIndex > 0) {
                    selectionModel.addSelectionInterval(selectionModel.getLeadSelectionIndex(),
                        nextCheckIndex);
                } else {
                    selectionModel.setLeadSelectionIndex(0);
                }
                nextCheckIndex--;
            }
            nextCheckIndex = selectionModel.getAnchorSelectionIndex() + 1;
            while (selectionModel.isSelectedIndex(nextCheckIndex) && (nextCheckIndex <= maxIndex)) {
                if (nextCheckIndex < maxIndex) {
                    selectionModel.addSelectionInterval(nextCheckIndex, selectionModel.getLeadSelectionIndex());
                    selectionModel.setAnchorSelectionIndex(nextCheckIndex);
                } else {
                    selectionModel.setAnchorSelectionIndex(
                        maxIndex);
                }
                nextCheckIndex++;
            }
        } else if (selectionModel.getAnchorSelectionIndex() < selectionModel.getLeadSelectionIndex()) {
            int nextCheckIndex = selectionModel.getLeadSelectionIndex() + 1;
            while (selectionModel.isSelectedIndex(nextCheckIndex) && (nextCheckIndex <= maxIndex)) {
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
            while (selectionModel.isSelectedIndex(nextCheckIndex) && (nextCheckIndex >= 0)) {
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
     * Returns true is both the index and the leadIndex of the selectionModel are smaller or bigger than the
     * ancestorIndex.
     *
     * @param   selectionModel  DOCUMENT ME!
     * @param   index           the index of interest
     *
     * @return  true if index and leadIndex are on the same side
     */
    public static boolean isOnSameSideFromAncestorAsLead(final ListSelectionModel selectionModel, final int index) {
        final int ancestor = selectionModel.getAnchorSelectionIndex();
        final int lead = selectionModel.getLeadSelectionIndex();
        if ((lead <= ancestor) && (index <= ancestor)) {
            return true;
        }
        if ((lead >= ancestor) && (index >= ancestor)) {
            return true;
        }
        return false;
    }

    /**
     * Returns true is the index is between the leadIndex and the ancestorIndex.
     *
     * @param   selectionModel  DOCUMENT ME!
     * @param   index           the index of interest
     *
     * @return  true if index is between the leadIndex and the ancestorIndex
     */
    public static boolean isBetweenOrEqualsLeadAndAncestor(final ListSelectionModel selectionModel, final int index) {
        final int ancestor = selectionModel.getAnchorSelectionIndex();
        final int lead = selectionModel.getLeadSelectionIndex();
        if ((ancestor <= index) && (index <= lead)) {
            return true;
        }
        if ((lead <= index) && (index <= ancestor)) {
            return true;
        }
        return false;
    }
}
