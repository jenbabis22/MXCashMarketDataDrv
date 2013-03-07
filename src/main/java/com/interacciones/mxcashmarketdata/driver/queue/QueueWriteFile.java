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

package com.interacciones.mxcashmarketdata.driver.queue;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interacciones.mxcashmarketdata.driver.process.MessageProcessing;
import com.interacciones.mxcashmarketdata.driver.process.impl.FileMessageProcessing;

public class QueueWriteFile {
	protected final static Log LOGGER = LogFactory.getLog(QueueWriteFile.class);
	
	private MessageProcessing messageProcessing = null;
	
	public QueueWriteFile() {
		messageProcessing = new FileMessageProcessing();
	}
	
	public void ReadQueue(ConcurrentLinkedQueue<String> msgQueue) {
        LOGGER.debug("Reading from queue... Sizing " + msgQueue.size());
        
        while (true) {
            Iterator<String> it = msgQueue.iterator();

            while (it.hasNext()) {
                String msg = (String) it.next();
                //messageProcessing.receive(msg.toString());
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
