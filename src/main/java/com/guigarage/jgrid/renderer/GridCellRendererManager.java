/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package com.guigarage.jgrid.renderer;

import java.awt.Component;

import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

/**
 * A manager for all renderers of a JGrid. It handles renderers for cellClasses. If no renderer for a specific class is
 * registered the global defaultrenderer will be used.
 *
 * @author   hendrikebbers
 * @version  $Revision$, $Date$
 */
public class GridCellRendererManager {

    //~ Instance fields --------------------------------------------------------

    private GridCellRenderer defaultRenderer;

    private Map<Class<?>, GridCellRenderer> rendererMapping;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GridCellRendererManager object.
     */
    public GridCellRendererManager() {
        rendererMapping = new HashMap<Class<?>, GridCellRenderer>();
        defaultRenderer = new DefaultGridCellRenderer();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Adds a renderer to the handler. The renderer is the default renderer for the cellClass <code>cls</code>
     *
     * @param  cls       set the renderer for this class
     * @param  renderer  the renderer for all instances of <code>cls</code>
     */
    public void addRendererMapping(final Class<?> cls, final GridCellRenderer renderer) {
        rendererMapping.put(cls, renderer);
    }

    /**
     * Deregisteres a existing renderer for class <code>cls.</code>
     *
     * @param  cls  remove the renderer for this class
     */
    public void removeRendererMapping(final Class<?> cls) {
        rendererMapping.remove(cls);
    }

    /**
     * Set the defaultRenderer. If no renderer is registered for a specific class the <code>defaultRenderer</code> will
     * be used.
     *
     * @param  defaultRenderer  the new defaultRenderer
     */
    public void setDefaultRenderer(final GridCellRenderer defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    /**
     * Returns the defaultRenderer. If no renderer is registered for a specific class the <code>defaultRenderer</code>
     * will be used.
     *
     * @return  the defaultRenderer
     */
    public GridCellRenderer getDefaultRenderer() {
        return defaultRenderer;
    }

    /**
     * Updates all UI class for all registered renderers. Must be called when the L&F changed.
     */
    public void updateRendererUI() {
        for (final GridCellRenderer renderer : rendererMapping.values()) {
            updateRendererTreeUI(renderer);
        }

        final GridCellRenderer renderer = getDefaultRenderer();
        updateRendererTreeUI(renderer);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  renderer  DOCUMENT ME!
     */
    private void updateRendererTreeUI(final GridCellRenderer renderer) {
        if (renderer instanceof Component) {
            SwingUtilities.updateComponentTreeUI((Component)renderer);
        }
    }

    /**
     * Returns the registered renderer for class <code>cls</code>. If no renderer is registered for a specific class the
     * <code>defaultRenderer</code> will be used.
     *
     * @param   cls  DOCUMENT ME!
     *
     * @return  the registered renderer or the defaultRenderer
     */
    public GridCellRenderer getRendererForClass(final Class<?> cls) {
        GridCellRenderer ret = defaultRenderer;

        if (rendererMapping.containsKey(cls)
                    && (rendererMapping.get(cls) != null)) {
            ret = rendererMapping.get(cls);
        }
        return ret;
    }
}
