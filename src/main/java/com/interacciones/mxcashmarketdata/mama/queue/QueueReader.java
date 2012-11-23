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

package com.interacciones.mxcashmarketdata.mama.queue;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interacciones.mxcashmarketdata.mama.message.Parser;

public class QueueReader {
	protected final static Log LOGGER=LogFactory.getLog(QueueReader.class);
	SendMessageToMama sendMessage = null;
	
	public QueueReader() {
		sendMessage = new SendMessageToMama();
	}

	public void ReadQueue(ConcurrentLinkedQueue<Parser> msgQueue){
		
		LOGGER.debug("Reading from queue... Sizing " + msgQueue.size());
        
		while (true){
			Iterator<Parser> it=msgQueue.iterator();
			
			while(it.hasNext()){
				Parser msg = (Parser)it.next();
				LOGGER.debug("Tipo de Mensaje: " + msg.TypeMessage());
				LOGGER.debug("Emisora :"+ msg.Emisora()); 
				sendMessage.sendMessage(msg);
				msgQueue.poll();
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				LOGGER.error("Error: " + e.getMessage());
			}
		}
	}
	
	public void close(){
		//TODO
	}
}

