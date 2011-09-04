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

import javax.swing.JScrollPane;

import de.guigarage.demos.tunes.TunesApp;
import de.guigarage.demos.tunes.views.renderer.BetterTunesAlbumGridRenderer;
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
		JScrollPane scrollPane = new JScrollPane(getGrid());
		scrollPane.setBorder(null);
		add(scrollPane);
	}

	public JGrid getGrid() {
		if(grid == null) {
			grid = new JGrid();
			grid.setModel(getModel());
			grid.setBorder(null);
			grid.setSelectionModel(getSelectionModel());
			grid.setSelectionBorderColor(new Color(43, 58, 88).brighter());
			grid.setSelectionBackground(new Color(34, 35, 37));
			grid.setCellBackground(new Color(34, 35, 37));
			grid.setBackground(new Color(34, 35, 37));
//			grid.getCellRendererManager().setDefaultRenderer(new TunesAlbumGridRenderer());
			grid.getCellRendererManager().setDefaultRenderer(new BetterTunesAlbumGridRenderer());

//			grid.setHorizontalAlignment(SwingConstants.LEFT);
			grid.setVerticalMargin(12);
			grid.setHorizonztalMargin(12);
			try {
				GestureUtilities.registerListener(grid, new GestureMagnificationListener() {

					@Override
					public void magnify(GestureMagnificationEvent gestureMagnificationEvent) {
						int min = 128;
						int max = 512;
						int val = grid.getFixedCellDimension();
//			
						int diff = (int) ((max - min) * gestureMagnificationEvent.getMagnification());
						
						grid.setFixedCellDimension(Math.max(min, Math.min(max, val + diff)));
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
