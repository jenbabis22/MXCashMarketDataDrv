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

public class UMessage extends Parser
{

    public UMessage(String message)
    {
        super(message);
        Message = message;
    }

    @Override
    public String TypeMessage()
    {
        return Message.substring(0, 2);
    }

    public String Muestra()
    {
        return Message.substring(2, 4);
    }

    public String Sector()
    {
        return Message.substring(4, 8);
    }

    public String Ramo()
    {
        return Message.substring(8, 12);
    }

    public String Hora()
    {
        return Message.substring(12, 18);
    }

    public String NOper()
    {
        return Message.substring(20, 26);
    }

    public String Volumen()
    {
        return Message.substring(26, 39);
    }

    public String Importe()
    {
        return Message.substring(39, 55);
    }

    public String Alzas()
    {
        return Message.substring(57, 61);
    }

    public String Bajas()
    {
        return Message.substring(61, 65);
    }

    public String SinCambio()
    {
        return Message.substring(65, 69);
    }

    public String Indice()
    {
        return Message.substring(69, 76);
    }

    public String Variacion()
    {
        return Message.substring(78, 84);
    }

    public String Porcentaje()
    {
        return Message.substring(86, 89);
    }

    public String Tendencia()
    {
        return Message.substring(91, 92);
    }

    public String EstadoIndice()
    {
        return Message.substring(92, 94);
    }

    private String Message;
}