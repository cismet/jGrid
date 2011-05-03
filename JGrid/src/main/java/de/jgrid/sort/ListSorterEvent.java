package de.jgrid.sort;

import javax.swing.event.RowSorterEvent.Type;

public class ListSorterEvent  extends java.util.EventObject {
    
	private static final long serialVersionUID = 1L;
	
	private Type type;
    private int[] oldViewToModel;

    public ListSorterEvent(ListSorter source) {
        this(source, Type.SORT_ORDER_CHANGED, null);
    }

    public ListSorterEvent(ListSorter source, Type type,
                          int[] previousRowIndexToModel) {
        super(source);
        if (type == null) {
            throw new IllegalArgumentException("type must be non-null");
        }
        this.type = type;
        this.oldViewToModel = previousRowIndexToModel;
    }

    public ListSorter getSource() {
        return (ListSorter)super.getSource();
    }

    public Type getType() {
        return type;
    }

    public int convertPreviousRowIndexToModel(int index) {
        if (oldViewToModel != null && index >= 0 &&
                index < oldViewToModel.length) {
            return oldViewToModel[index];
        }
        return -1;
    }

    public int getPreviousRowCount() {
        return (oldViewToModel == null) ? 0 : oldViewToModel.length;
    }
}
