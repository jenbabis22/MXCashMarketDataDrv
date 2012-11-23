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


package com.interacciones.mxcashmarketdata.driver.process.impl;


import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interacciones.mxcashmarketdata.driver.process.DriverServerHandler;
import com.interacciones.mxcashmarketdata.driver.process.MessageProcessing;
import com.interacciones.mxcashmarketdata.mama.message.KMessage;
import com.interacciones.mxcashmarketdata.mama.message.OMessage;
import com.interacciones.mxcashmarketdata.mama.message.PMessage;
import com.interacciones.mxcashmarketdata.mama.message.Parser;
import com.interacciones.mxcashmarketdata.mama.queue.QueueReader;
//This class deciphers the different types of messages coming from the BMV, creates an object based on the message type, and places it in a Queue.
public class QueueMessageProcessing implements MessageProcessing{
	protected final static Log LOGGER=LogFactory.getLog(DriverServerHandler.class);
	private Thread queue = null;
	private static int MSG_TYPE 	  = 28;
	private static String MSG_O = "O";
	private static String MSG_DO = "DO";
	private static String MSG_P = "P";
	private static String MSG_K = "K";
	 /**
     * Queue 
     */
    private QueueReader QueueReader;
	private ConcurrentLinkedQueue<Parser> MsgQueue;
	
	public QueueMessageProcessing(){
		/**
		 * Queue
		 * Creation Thread
		 */
		MsgQueue = new ConcurrentLinkedQueue<Parser>();
		QueueReader = new QueueReader();
	}
	
	@Override
	public void receiv(String message) {
		String typeMessage = message.substring(MSG_TYPE, MSG_TYPE+2).trim();
		LOGGER.debug("Type Message: " + typeMessage);
		
		String NewMsg = message.substring(MSG_TYPE);
		if( typeMessage.equals(MSG_O) || typeMessage.equals(MSG_DO)){ 
			OMessage MSG = new OMessage(NewMsg);
			MsgQueue.add(MSG);
			LOGGER.debug("Emisora " + MSG.Emisora());
		}else if (typeMessage.equals(MSG_P)){
			PMessage MSG = new PMessage(NewMsg); 
			MsgQueue.add(MSG);
			LOGGER.debug("Emisora " + MSG.Emisora());
		}else if (typeMessage.equals(MSG_K)){
			KMessage MSG = new KMessage(NewMsg);
			MsgQueue.add(MSG);
			LOGGER.debug("Emisora " + MSG.Emisora());
		}else{
			//TODO
		}
	}

	@Override
	public void close() {
		MsgQueue.clear();	
	}

	@Override
	public void init() {
		queue = new Thread(this.ReadFromQueue);
		queue.start();
	}
	
	//Parse Data
  	Runnable ReadFromQueue =  new Runnable() {
  		public void run(){
  			QueueReader.ReadQueue(MsgQueue);
  		}
  	};
}

