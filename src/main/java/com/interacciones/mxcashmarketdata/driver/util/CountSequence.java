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

package com.interacciones.mxcashmarketdata.driver.util;

import java.util.concurrent.atomic.AtomicLong;

public class CountSequence {

    protected CountSequence() {
        sequence = new AtomicLong(2);
    }

    static private CountSequence instance;

    private AtomicLong sequence;

    /**
     * @return sequence and increment
     */
    public long next() {
        return sequence.getAndIncrement();
    }

    public void setValue(long count) {
        sequence.getAndSet(count);
    }

    public long getValue() {
        return sequence.get();
    }

    static public CountSequence getInstance() {
        if (instance == null) {
            instance = new CountSequence();
        }
        return instance;
    }
}

