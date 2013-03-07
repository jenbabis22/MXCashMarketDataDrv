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

public class Parser {
    private String Message;
    private String CompleteMsg;
    private long sequence;

    public Parser(String message) {
        this.Message = message;
    }

    public String TypeMessage() {
        return Message.substring(0, 2);
    }

    public String TypeValue() {
        return "";
    }

    public String Emisora() {
        return "";
    }

    public String Serie() {
        return "";
    }

    public double AskPrice() {
        return 0;
    }

    public long AskSize() {
        return 0;
    }

    public long BidSize() {
        return 0;
    }

    public double BidPrice() {
        return 0;
    }

    public String QuoteTime() {
        return "";
    }

    public void setSequence( long sec ){
        this.sequence = sec;
    }

    public long getSequence( ){
        return this.sequence;
    }

    public void setCompleteMsg( String  msg ){
        this.CompleteMsg = msg;
    }

    public String getCompleteMsg(){
        return this.CompleteMsg;
    }
}

