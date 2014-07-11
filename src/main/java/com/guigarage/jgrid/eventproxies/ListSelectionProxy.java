/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * Created on July 20, 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2011 Hendrik Ebbers
 */
package com.guigarage.jgrid.eventproxies;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Proxyclass for ListSelectionListeners. All ListSelectionEvents posted to an instance will transmited to the
 * ListSelectionListeners registered at the proxy.
 *
 * @author   hendrikebbers
 * @version  $Revision$, $Date$
 */
public class ListSelectionProxy implements ListSelectionListener {

    //~ Instance fields --------------------------------------------------------

    private List<ListSelectionListener> listenerList;

    //~ Methods ----------------------------------------------------------------

    /**
     * Registers a ListSelectionListener to the proxy.
     *
     * @param  l  the ListSelectionListener to register
     */
    public void addListSelectionListener(final ListSelectionListener l) {
        if (listenerList == null) {
            listenerList = new ArrayList<ListSelectionListener>();
        }
        listenerList.add(l);
    }

    /**
     * Deregisters a ListSelectionListener to the proxy.
     *
     * @param  l  the ListSelectionListener to deregister
     */
    public void removeListSelectionListener(final ListSelectionListener l) {
        if (listenerList != null) {
            listenerList.add(l);
        }
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        if (listenerList != null) {
            for (final ListSelectionListener listener : listenerList) {
                listener.valueChanged(e);
            }
        }
    }
}
