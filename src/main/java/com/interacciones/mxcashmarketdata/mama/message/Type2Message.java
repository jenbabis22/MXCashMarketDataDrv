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

public class Type2Message extends Parser
{

    public Type2Message(String Msg)
    {
        super(Msg);
        this.Msg = Msg;
    }

    public String TypeMessage()
    {
        return Msg.substring(0, 2);
    }

    public String Trans()
    {
        return Msg.substring(2, 3);
    }

    public String Movimiento()
    {
        return Msg.substring(3, 4);
    }

    @Override
    public String Emisora()
    {
        return Msg.substring(4, 11);
    }

    @Override
    public String Serie()
    {
        return Msg.substring(11, 16);
    }

    @Override
    public String TypeValue()
    {
        return Msg.substring(16, 20);
    }

    public String Volumen()
    {
        return Msg.substring(20, 28);
    }

    public String Precio()
    {
        return Msg.substring(28, 34);
    }

    public String PrecioAnteriorHecho()
    {
        return Msg.substring(40, 46);
    }

    public String PrecioAnteriorOF()
    {
        return Msg.substring(52, 58);
    }

    public String Vende()
    {
        return Msg.substring(64, 69);
    }

    public String Compra()
    {
        return Msg.substring(69, 74);
    }

    public String TiempoSuspensionMin()
    {
        return Msg.substring(74, 76);
    }

    public String HoraSuspension()
    {
        return Msg.substring(76, 80);
    }

    public String Variacion()
    {
        return Msg.substring(80, 82);
    }

    public String Tendencia()
    {
        return Msg.substring(84, 85);
    }

    public String SeleccionadaIPC()
    {
        return Msg.substring(85, 87);
    }

    public String OficioCNV()
    {
        return Msg.substring(87, 93);
    }

    public String HoraFinSuspension()
    {
        return Msg.substring(93, 97);
    }

    public String Contenido()
    {
        return Msg.substring(97, 101);
    }

    private String Msg;
}