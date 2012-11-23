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

public class PMessage extends Parser {
	private String Message;
	
	public PMessage(String message) {
		super(message);
		Message = message;
	}
	
	//Methods
	public String TypeMessage(){
		return Message.substring(0, 2);
	}

	public String Trans() {//unused
		return Message.substring(2,3);
	}
	
	public String Folio(){
		return Message.substring(3,8);
	}
	
	public String Hora(){
		return Message.substring(8,12);
	}

	public String TypeValue(){	
		return Message.substring(12, 16);
        }
	
	public String Emisora(){	
		return Message.substring(16,23);
        }

	public String Serie(){		
		return Message.substring(23,28);	
	}

	public String Volumen(){
		return Message.substring(28, 39);
	}
	
	public String Precio(){
		return Message.substring(39,45).concat(".").concat(Message.substring(45,51));
	}
}

