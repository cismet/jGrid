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

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Proxyclass for ListDataListeners. All ListDataEvent posted to an instance will transmited to the ListDataListeners
 * registered at the proxy.
 *
 * @author   hendrikebbers
 * @version  $Revision$, $Date$
 */
public class ListDataProxy implements ListDataListener {

    //~ Instance fields --------------------------------------------------------

    private List<ListDataListener> listenerList;

    //~ Methods ----------------------------------------------------------------

    /**
     * Registers a ListDataListener to the proxy.
     *
     * @param  l  the ListDataListener to register
     */
    public void addListDataListener(final ListDataListener l) {
        if (listenerList == null) {
            listenerList = new ArrayList<ListDataListener>();
        }
        listenerList.add(l);
    }

    /**
     * Deregisters a ListDataListener to the proxy.
     *
     * @param  l  the ListDataListener to deregister
     */
    public void removeListDataListener(final ListDataListener l) {
        if (listenerList != null) {
            listenerList.add(l);
        }
    }

    @Override
    public void intervalAdded(final ListDataEvent e) {
        if (listenerList != null) {
            for (final ListDataListener listener : listenerList) {
                listener.intervalAdded(e);
            }
        }
    }

    @Override
    public void intervalRemoved(final ListDataEvent e) {
        if (listenerList != null) {
            for (final ListDataListener listener : listenerList) {
                listener.intervalRemoved(e);
            }
        }
    }

    @Override
    public void contentsChanged(final ListDataEvent e) {
        if (listenerList != null) {
            for (final ListDataListener listener : listenerList) {
                listener.contentsChanged(e);
            }
        }
    }
}
