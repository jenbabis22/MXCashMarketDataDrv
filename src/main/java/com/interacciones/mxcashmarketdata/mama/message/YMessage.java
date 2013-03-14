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
public class YMessage extends Parser {

    public YMessage(String message) {
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

    public String EMISORA1() {
        return Message.substring(18, 25);
    }
    
    public String EMISION_O_SERIE_SUBYACENTE () {
        return Message.substring(25, 30);
    }
     
    public String TIPO_VALOR_SUBYACENTE () {
        return Message.substring(30, 32);
    }
    
     public String NUM_VAL_INSCRITOS  () {
        return Message.substring(32, 43);
    }
    
    
  
    private String Message;
}