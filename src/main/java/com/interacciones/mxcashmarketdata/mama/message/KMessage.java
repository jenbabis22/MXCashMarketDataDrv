// $Id$
// MXCashOpenMamaDrv - An OpenMama based driver for the Mexican Cash Market Binary Feed
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

public class KMessage extends Parser{
	private String Msg;
	
	public KMessage(String Msg) {
		super(Msg);
		this.Msg = Msg;
	}
	
	//Methods
	public String TypeMessage(){
		return Msg.substring(0, 2);
	}
	
	public String TypeValue(){
		return Msg.substring(2, 6);
	}
	
	public String Emisora(){
		return Msg.substring(6, 13);
	}
	
	public String Serie(){
		return Msg.substring(13, 18);
	}
	
	public String Sector(){
		return Msg.substring(18, 20);
	}
	
	public String Subsector(){
		return Msg.substring(20, 22);
	}
	
	public String Ramo(){
		return Msg.substring(22, 24);
	}
	
	public String Subramo(){
		return Msg.substring(24, 26);
	}
	
	public String UltimoPrecio(){
		return Msg.substring(26,32).concat(".").concat(Msg.substring(32,38));
	}
	
	public String AccionesEnCirculacion(){
		return Msg.substring(38,56);
	}
	
	public String PrecioPromedioPonderado(){
		return Msg.substring(56,62).concat(".").concat(Msg.substring(62,68));
	}
	
	public String Referencia(){
		return Msg.substring(68,70);
	}
	
	public String FechaDeReferencia(){
		return Msg.substring(70, 78);
	}
	
	public String IndMuestra(){
		return Msg.substring(78, 80);
	}
	
	public String CuponVigente(){
		return Msg.substring(80, 84);
	}
	
	public String TipoMercado(){
		return Msg.substring(84, 86);
	}
	
	public String PaisDelmMercadoDeOrigen(){
		return Msg.substring(86,101);
	}
	
	public String MonedaPaisOrigen(){
		return Msg.substring(101, 111);
	}
	
	public String MercadoDeCotizacionPrincipal(){
		return Msg.substring(111, 121);
	}
	
	public String PaisMercadoDeCotizacionPrinicpal(){
		return Msg.substring(121, 136);
	}
	
	public String MonedaMercadodeCotizacionPrincipal(){
		return Msg.substring(136, 146);
	}
	
	public String ISIN(){
		return Msg.substring(146, 158);
	}
}

