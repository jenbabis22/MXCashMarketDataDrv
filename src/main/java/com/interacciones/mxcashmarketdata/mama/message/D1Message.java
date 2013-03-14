// $Id$
// MXCashMarketDataDrv - An OpenMama based driver for the Mexican Cash Market Binary Feed
// Copyright (c) 2013 SPT Software 
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


// Referenced classes of package com.interacciones.mxcashmarketdata.mama.message:
//            Parser

public class D1Message extends Parser
{

    public D1Message(String message)
    {
        super(message);
        Msg = message;
    }

    public String TypeMessage()
    {
        return Msg.substring(0, 2);
    }

    public String TypeValue()
    {
        return Msg.substring(2, 6);
    }

    public String Emisora()
    {
        return Msg.substring(6, 13);
    }

    public String Serie()
    {
        return Msg.substring(13, 18);
    }

    public String PrecioCOM1()
    {
        return Msg.substring(18, 24);
    }

    public String NumOrdenesCOM1()
    {
        return Msg.substring(27, 29);
    }

    public String VolumenAcumuladoCOM1()
    {
        return Msg.substring(29, 38);
    }

    public String PrecioCOM2()
    {
        return Msg.substring(18, 24);
    }

    public String NumOrdenesCOM2()
    {
        return Msg.substring(27, 29);
    }

    public String VolumenAcumuladoCOM2()
    {
        return Msg.substring(29, 38);
    }

    private String Msg;
}