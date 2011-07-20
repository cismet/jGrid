package de.jgrid.renderer;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

/**
 * A manager for all renderers of a JGrid. It handles renderers for cellClasses.
 * If no renderer for a specific class is registered the global defaultrenderer
 * will be used.
 * 
 * @author hendrikebbers
 * 
 */
public class GridCellRendererManager {

	private GridCellRenderer defaultRenderer;

	private Map<Class<?>, GridCellRenderer> rendererMapping;

	public GridCellRendererManager() {
		rendererMapping = new HashMap<Class<?>, GridCellRenderer>();
		defaultRenderer = new DefaultGridCellRenderer();
	}

	/**
	 * Adds a renderer to the handler. The renderer is the default renderer for
	 * the cellClass <code>cls</code>
	 * 
	 * @param cls
	 *            set the renderer for this class
	 * @param renderer
	 *            the renderer for all instances of <code>cls</code>
	 */
	public void addRendererMapping(Class<?> cls, GridCellRenderer renderer) {
		rendererMapping.put(cls, renderer);
	}

	/**
	 * Deregisteres a existing renderer for class <code>cls</code>
	 * @param cls remove the renderer for this class
	 */
	public void removeRendererMapping(Class<?> cls) {
		rendererMapping.remove(cls);
	}

	/**
	 * Set the defaultRenderer. If no renderer is registered for a specific class the <code>defaultRenderer</code> will be used.
	 * @param defaultRenderer the new defaultRenderer
	 */
	public void setDefaultRenderer(GridCellRenderer defaultRenderer) {
		this.defaultRenderer = defaultRenderer;
	}

	/**
	 * Returns the defaultRenderer. If no renderer is registered for a specific class the <code>defaultRenderer</code> will be used.
	 * @return the defaultRenderer
	 */
	public GridCellRenderer getDefaultRenderer() {
		return defaultRenderer;
	}

	/**
	 * Updates all UI class for all registered renderers. Must be called when the L&F changed.
	 */
	public void updateRendererUI() {
		for (GridCellRenderer renderer : rendererMapping.values()) {
			updateRendererTreeUI(renderer);
		}

		GridCellRenderer renderer = getDefaultRenderer();
		updateRendererTreeUI(renderer);
	}

	private void updateRendererTreeUI(GridCellRenderer renderer) {
		if (renderer instanceof Component) {
			SwingUtilities.updateComponentTreeUI((Component) renderer);
		}
	}

	/**
	 * Returns the registered renderer for class <code>cls</code>. If no renderer is registered for a specific class the <code>defaultRenderer</code> will be used.
	 * @param cls
	 * @return the registered renderer or the defaultRenderer
	 */
	public GridCellRenderer getRendererForClass(Class<?> cls) {
		GridCellRenderer ret = defaultRenderer;

		if (rendererMapping.containsKey(cls)
				&& rendererMapping.get(cls) != null) {
			ret = rendererMapping.get(cls);
		}
		return ret;
	}
}
