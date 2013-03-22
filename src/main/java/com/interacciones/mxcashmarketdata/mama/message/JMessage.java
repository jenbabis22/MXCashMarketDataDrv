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
package com.interacciones.mxcashmarketdata.mama.message;

public class JMessage extends Parser {

    private String Msg;

    public JMessage(String message) {
        super(message);
        Msg = message;
    }

    //Methods
    @Override
    public String TypeMessage() {
        return Msg.substring(0, 2);
    }

    @Override
    public String TypeValue() {
        return Msg.substring(2, 6);
    }

    @Override
    public String Emisora() {
        return Msg.substring(6, 13);
    }

    @Override
    public String Serie() {
        return Msg.substring(13, 18);
    }

    public String FechaEmision() {
        return Msg.substring(18, 26);
    }

    public String FechaVencimiento() {
        return Msg.substring(26, 34);
    }
}
