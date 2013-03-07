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

public class EMessage extends Parser
{
     private String Msg;

    public EMessage(String Msg)
    {
         super(Msg);
        this.Msg = Msg;
    }
    

    @Override
    public String TypeMessage()
    {
        return Msg.substring(0, 2);
    }

    @Override
    public String TypeValue()
    {
        return Msg.substring(2, 6);
    }

    @Override
    public String Emisora()
    {
        return Msg.substring(6, 13);
    }

    @Override
    public String Serie()
    {
        return Msg.substring(13, 18);
    }

    public String TipoOperacion()
    {
        return Msg.substring(18, 20);
    }

    public String Cupon()
    {
        return Msg.substring(20, 24);
    }

    public String NumeroDeOperaciones()
    {
        return Msg.substring(24, 28);
    }

    public String Volumen()
    {
        return Msg.substring(28, 41);
    }

    public String Importe()
    {
        return Msg.substring(41, 56).concat(".").concat(Msg.substring(56, 59));
    }

    public String Apertura()
    {
        return Msg.substring(59, 65).concat(".").concat(Msg.substring(65, 71));
    }

    public String Maximo()
    {
        return Msg.substring(71, 77).concat(".").concat(Msg.substring(77, 83));
    }

    public String Minimo()
    {
        return Msg.substring(83, 89).concat(".").concat(Msg.substring(89, 95));
    }

    public String Promedio()
    {
        return Msg.substring(95, 101).concat(".").concat(Msg.substring(101, 107));
    }

    public String Ultimo()
    {
        return Msg.substring(107, 113).concat(".").concat(Msg.substring(113, 119));
    }

    public String Variacion()
    {
        return Msg.substring(119, 125).concat(".").concat(Msg.substring(125, 131));
    }

    public String Tendencia()
    {
        return Msg.substring(131, 132);
    }

    public String Porcentaje()
    {
        return Msg.substring(132, 135).concat(".").concat(Msg.substring(135, 137));
    }

    public String Referencia()
    {
        return Msg.substring(137, 139);
    }

    public String FechaUltimaReferencia()
    {
        return Msg.substring(139, 147);
    }

    public String PrecioMaximoUlt12Meses()
    {
        return Msg.substring(147, 153).concat(".").concat(Msg.substring(153, 159));
    }

    public String PrecioMinimoUlt12Meses()
    {
        return Msg.substring(159, 165).concat(".").concat(Msg.substring(165, 171));
    }

    public String PrecioUlitmaReferencia()
    {
        return Msg.substring(171, 177).concat(".").concat(Msg.substring(177, 183));
    }

   
}