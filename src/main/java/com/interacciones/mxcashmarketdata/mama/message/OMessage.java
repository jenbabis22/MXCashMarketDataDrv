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

public class OMessage extends Parser {
	private String Msg;

	public OMessage(String message) {
		super(message);
		Msg = message;
	}
	
	//Methods
	public String TypeMessage(){
		return Msg.substring(0,2);
	}
	
	public String Trans(){
		return Msg.substring(2,3);
	}
	
	public String TypeValue(){
		return Msg.substring(3,7);
	}
	
	public String Emisora(){
		return Msg.substring(7,14);
	}
		
	public String Serie(){
		return Msg.substring(14,19);
	}
	
	public String TipoOperacion(){
		return Msg.substring(20,22);
	}
	
	public String DiasPlazo(){
		return Msg.substring(22,25);
	}
	
	public String Liquidacion(){
		return Msg.substring(25,27);
	}
	
	public String TipoMercado(){
		return Msg.substring(27,29);
	}
	
	public long AskSize(){
		return new Long(Msg.substring(29,40));
	}
	
	public double AskPrice(){
		return new Double(Msg.substring(40,46).concat(".").concat(Msg.substring(46,52)));
	}
	
	public long BidSize(){
		return new Long(Msg.substring(52,63));
	}
	

	public double BidPrice(){
		return new Double(Msg.substring(63,69).concat(".").concat(Msg.substring(69,75)));
	}
	
	public String QuoteTime(){
		return Msg.substring(71,75);
	}
	
	public String Cupon(){
		return Msg.substring(75,79);
	}
}

