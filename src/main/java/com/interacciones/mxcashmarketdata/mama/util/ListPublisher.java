// $Id$
// MXCashMarketDataDrv - An OpenMama based driver for the Mexican Cash Market Binary Feed
// Copyright (c) 2012 Interacciones Casa de Bolsa
/* 
  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.
 
  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.
 
  You should have received a copy of the GNU Lesser General Public
  License along with this library; if not, write to the Free Software
  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

package com.interacciones.mxcashmarketdata.mama.util;

import com.wombat.mama.MamaPublisher;
import com.wombat.mama.MamaTransport;

import java.util.HashMap;

public class ListPublisher {
    static private ListPublisher instance;
    private HashMap<String, MamaPublisher> mapPublisher = null;
    private MamaTransport transport = null;

    private ListPublisher() {
        mapPublisher = new HashMap<String, MamaPublisher>();
    }

    static public ListPublisher getInstance() {
        if (instance == null) {
            instance = new ListPublisher();
        }
        return instance;
    }

    public void set(String topic) {
        MamaPublisher publisher = new MamaPublisher();
        publisher.create(getTransport(), topic);
        mapPublisher.put(topic, publisher);
    }

    public MamaPublisher get(String topic) {
        MamaPublisher publisher = (MamaPublisher) mapPublisher.get(topic);
        if (publisher == null) {
            //set(topic);
            publisher = new MamaPublisher();
            publisher.create(getTransport(), topic);
            mapPublisher.put(topic, publisher);
        }
        return publisher;
    }

    public void clear() {
        mapPublisher.clear();
    }

    public HashMap<String, MamaPublisher> getAll() {
        return mapPublisher;
    }

    @Override
    public String toString() {
        return "ListPublisher [mapPublisher=" + mapPublisher + "]";
    }

    public MamaTransport getTransport() {
        return transport;
    }

    public void setTransport(MamaTransport transport) {
        this.transport = transport;
    }
}

