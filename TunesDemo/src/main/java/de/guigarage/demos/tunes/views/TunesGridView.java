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

import de.guigarage.demos.tunes.TunesApp;
import de.guigarage.demos.tunes.views.renderer.TunesAlbumGridRenderer;
import de.guigarage.gestures.GestureMagnificationEvent;
import de.guigarage.gestures.GestureMagnificationListener;
import de.guigarage.gestures.GestureUtilities;
import de.guigarage.gestures.GesturesNotSupportedException;
import de.jgrid.JGrid;

public class TunesGridView extends AbstractTunesDataView {

	private static final long serialVersionUID = 1L;

	private JGrid grid;
	
	public TunesGridView(TunesApp app) {
		super(app);
		add(getGrid());
	}

	public JGrid getGrid() {
		if(grid == null) {
			grid = new JGrid();
			grid.setModel(getModel());
			grid.setSelectionModel(getSelectionModel());
			grid.setSelectionBorderColor(new Color(43, 58, 88).brighter());
			grid.setBackground(new Color(34, 35, 37));
			grid.getCellRendererManager().setDefaultRenderer(new TunesAlbumGridRenderer());
			
			try {
				GestureUtilities.registerListener(grid, new GestureMagnificationListener() {

					@Override
					public void magnify(GestureMagnificationEvent gestureMagnificationEvent) {
						int min = 63;
						int max = 256;
						int val = grid.getFixedCellDimension();
//			
						
						System.out.println(gestureMagnificationEvent.getMagnification());
						System.out.println((max - min) * gestureMagnificationEvent.getMagnification());
						
						grid.setFixedCellDimension((int) (val + (max - min) * gestureMagnificationEvent.getMagnification()));
					}
				});
			} catch (GesturesNotSupportedException e2) {
				e2.printStackTrace();
			}
			
			new DropTarget (grid, new DropTargetAdapter() {
				
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
		return grid;
	}
}
