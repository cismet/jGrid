package de.guigarage.demos.tunes.views;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;

import de.guigarage.demos.tunes.TunesApp;
import de.guigarage.demos.tunes.views.renderer.TunesAlbumListRenderer;

public class TunesListView extends AbstractTunesDataView {

	private static final long serialVersionUID = 1L;

	private JList list;
	
	private JScrollPane scrollPane;
	
	public TunesListView(TunesApp app) {
		super(app);
		add(getScrollPane());
	}
	
	public JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane(getList());
			scrollPane.setBorder(null);
		}
		return scrollPane;
	}
	
	public JList getList() {
		if(list == null) {
			list = new JList(getModel());
			list.setSelectionModel(getSelectionModel());
			list.setCellRenderer(new TunesAlbumListRenderer());
			list.setBackground(new Color(34, 35, 37));
			list.setDragEnabled(true);
			new DropTarget (list, new DropTargetAdapter() {
				
				@Override
				public void drop(DropTargetDropEvent dtde) {
					 try
				      {
				         Transferable tr = dtde.getTransferable();

				         if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
				        	dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				            @SuppressWarnings("unchecked")
							List<File> data = (List<File>)tr.getTransferData(DataFlavor.javaFileListFlavor);
				            for (File f : data) {
								getModel().addByFile(f);
							}
				            dtde.getDropTargetContext().dropComplete(true);
				         }else{
				            System.err.println ("DataFlavor.stringFlavor is not supported, rejected");
				            dtde.rejectDrop();
				         } 
				      }catch (Exception ex) {
				         ex.printStackTrace();
				         dtde.rejectDrop();
				      }
				}
			});
		}
		return list;
	}
}
