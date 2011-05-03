package de.jgrid.renderer;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

public class GridCellRendererManager {

	private GridCellRenderer defaultRenderer;
	
	private Map<Class<?>, GridCellRenderer> rendererMapping;
	
	private List<GridRendererManagerListener> listeners;
	
	public GridCellRendererManager() {
		listeners = new ArrayList<GridRendererManagerListener>();
		rendererMapping = new HashMap<Class<?>, GridCellRenderer>();
		defaultRenderer = new DefaultGridCellRenderer();
	}
	
	public void addGridRendererManagerListener(GridRendererManagerListener l) {
		listeners.add(l);
	}
	
	public void removeGridRendererManagerListener(GridRendererManagerListener l) {
		listeners.remove(l);
	}
	
	public void addRendererMapping(Class<?> cls, GridCellRenderer renderer) {
		rendererMapping.put(cls, renderer);	
	}
	
	public void removeRendererMapping(Class<?> cls) {
		rendererMapping.remove(cls);	
	}
	
	public void setDefaultRenderer(GridCellRenderer defaultRenderer) {
		this.defaultRenderer = defaultRenderer;
	}
	
	public GridCellRenderer getDefaultRenderer() {
		return defaultRenderer;
	}
	
	//Damit bei einem neuen UI etc. alles angepasst wird (ist ja nicht im ComponentTree....)
	public void updateRendererUI() {
		for (GridCellRenderer renderer : rendererMapping.values()) {
			updateRendererTreeUI(renderer);
		}
		
		GridCellRenderer renderer = getDefaultRenderer();
		updateRendererTreeUI(renderer);
	}
	
	private void updateRendererTreeUI(GridCellRenderer renderer) {
		if(renderer instanceof Component) {
			SwingUtilities.updateComponentTreeUI((Component) renderer);
		}
	}

	public GridCellRenderer getRendererForClass(Class<?> cls) {
		GridCellRenderer ret = defaultRenderer;
		
		if(rendererMapping.containsKey(cls) && rendererMapping.get(cls) != null) {
			ret = rendererMapping.get(cls);
		}	
		return ret;
	}
}
