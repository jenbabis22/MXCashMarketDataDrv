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
public class XMessage extends Parser {

    public XMessage(String message) {
        super(message);
        Message = message;
    }

    @Override
    public String TypeMessage() {
        return Message.substring(0, 2);
    }

    @Override
    public String Emisora() {
        return Message.substring(2, 9);
    }

    public String Emisora_O_Serie() {
        return Message.substring(9, 14);
    }

    @Override
    public String TypeValue() {
        return Message.substring(14, 18);
    }

    public String TipoWarrant() {
        return Message.substring(18, 19);
    }

    public String TipoLiquidacion() {
        return Message.substring(19, 20);
    }

    public String NUM_VAL_INSCRITOS() {
        return Message.substring(20, 35);
    }
    
     public String NUM_VAL_EJERCIDOS_AYER() {
        return Message.substring(35, 50);
    }
     
      public String NUM_VAL_EJERCIDOS_HOY() {
        return Message.substring(50, 65);
    }
      
       public String NUM_VAL_CIRCULACION() {
        return Message.substring(65, 80);
    }
    
    private String Message;
}