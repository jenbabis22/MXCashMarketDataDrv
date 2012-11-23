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


package com.interacciones.mxcashmarketdata.driver.command;

import com.interacciones.mxcashmarketdata.driver.util.CountSequence;
import com.interacciones.mxcashmarketdata.driver.remote.CommandRemote;

public class Retransmission extends CommandRemote {
	private static CountSequence countSequence;

	public Retransmission() {}
	
	@Override
	public String Usage() {
		String usage = "|sequence | sequence {show, -reset [long{7}]}|";
		return usage;
	}

	@Override
	public String execute(String[] args) {
		String responseCommand="Invalid Command";
		
		if(args[0].equalsIgnoreCase("sequence") && args.length==1){
			responseCommand = Usage();
		}else{
			for (int j = 1; j < args.length; j++) {
				System.out.println("Argument: " + args[j]);
				
				if(args[j].equalsIgnoreCase("show")){
					responseCommand = "Sequence  ".concat(String.valueOf(getSequence()-1));
					break;
				}else if(args[j].equalsIgnoreCase("-reset")){
					if(args[j+1]==null){
						responseCommand = "Invalid Argument";
						break;
					}
					countSequence.setValue(Long.parseLong(args[j+1])+1);
					responseCommand = "Sequence ".concat(String.valueOf(countSequence.getValue()-1));
					break;
				}
			}
		}
	
		return responseCommand;
	}

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return "sequence";
	}

	@Override
	public String getDescripcion() {
		String desc = "Message Retransmission in sequence number";
		return desc;
	}

	@Override
	public void validacion() {
		// TODO Auto-generated method stub
		
	}

	public static long getSequence() {
		return countSequence.getValue();
	}

	public static CountSequence getCountSequence() {
		return countSequence;
	}

	public static void setCountSequence(CountSequence countSequence) {
		Retransmission.countSequence = countSequence;
	}
}

