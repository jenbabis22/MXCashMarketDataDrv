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


import com.interacciones.mxcashmarketdata.driver.process.DriverServerHandler;
import com.interacciones.mxcashmarketdata.driver.process.MessageProcessing;
import com.interacciones.mxcashmarketdata.driver.util.Parameters;
import com.interacciones.mxcashmarketdata.driver.util.MessageType;
import com.interacciones.mxcashmarketdata.mama.message.KMessage;
import com.interacciones.mxcashmarketdata.mama.message.OMessage;
import com.interacciones.mxcashmarketdata.mama.message.PMessage;
import com.interacciones.mxcashmarketdata.mama.message.Parser;
import com.interacciones.mxcashmarketdata.mama.queue.QueueReader;
import com.interacciones.mxcashmarketdata.driver.queue.QueueWriteFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

//This class deciphers the different types of messages coming from the BMV, creates an object based on the message type, and places it in a Queue.
public class QueueMessageProcessing implements MessageProcessing {
    protected final static Log LOGGER = LogFactory.getLog(DriverServerHandler.class);
    private Thread queue = null;
    private Thread queueFile = null;
    private static int MSG_TYPE = 28;
    private static String MSG_O = "O";
    private static String MSG_DO = "DO";
    private static String MSG_P = "P";
    private static String MSG_K = "K";
    /**
     * Queue OpenMama
     */
    private QueueReader QueueReader;
    private ConcurrentLinkedQueue<Parser> MsgQueue;

    /**
     * Queue File
     */
    private QueueWriteFile queueWrite;
    private ConcurrentLinkedQueue<String> msgQueueFile;

    public QueueMessageProcessing() {
        /**
         * Queue
         * Creation Thread
         */
        MsgQueue = new ConcurrentLinkedQueue<Parser>();
        QueueReader = new QueueReader();

	queueWrite = new QueueWriteFile();
        msgQueueFile = new ConcurrentLinkedQueue<String>();
    }

    @Override
    public void receive( String message, long sequence ) {
	/**
    	 * message in file
    	 */
    	msgQueueFile.add(new String(message));
    	
    	/**
    	 * message in OpenMama
    	 */
        String typeMessage = message.substring(MSG_TYPE, MSG_TYPE + 2).trim();
        LOGGER.debug("Type Message: " + typeMessage);
        Parameters.LogMensaje( message );

        if( Parameters.Publish() ){
            String NewMsg = message.substring( MSG_TYPE );
            if( ( typeMessage.equals( MSG_O ) && MessageType.O.esPublicable() )
             || ( typeMessage.equals( MSG_DO ) && MessageType.DO.esPublicable() ) ) {
                OMessage MSG = new OMessage( NewMsg );
                MSG.setSequence( sequence );
                MSG.setCompleteMsg( message );
                MsgQueue.add( MSG );
                LOGGER.debug( "Emisora " + MSG.Emisora() );
            } else if( typeMessage.equals( MSG_P ) && MessageType.P.esPublicable() ) {
                PMessage MSG = new PMessage(NewMsg);
                MSG.setSequence( sequence );
                MSG.setCompleteMsg( message );
                MsgQueue.add( MSG );
                LOGGER.debug( "Emisora " + MSG.Emisora() );
            } else if( typeMessage.equals( MSG_K ) && MessageType.P.esPublicable() ) {
                KMessage MSG = new KMessage( NewMsg );
                MSG.setSequence( sequence );
                MSG.setCompleteMsg( message );
                MsgQueue.add( MSG );
                LOGGER.debug( "Emisora " + MSG.Emisora() );
            } else {
                //TODO
                LOGGER.info( "Mensaje descartado de tipo " + typeMessage );
            }
        }
    }
    @Override
    public void close() {
        // TODO
    }

    @Override
    public void init() {
        queue = new Thread(this.ReadFromQueue);
        queue.start();

        queueFile = new Thread(ReadFromQueueWrite);
        queueFile.start();
    }
	
	private boolean ValidaCS( String message ){
        boolean res = true;
        //ToDO
        return res;
    }

    //Parse Data
    Runnable ReadFromQueue = new Runnable() {
        public void run() {
            QueueReader.ReadQueue(MsgQueue);
        }
    };

    Runnable ReadFromQueueWrite = new Runnable() {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		queueWrite.ReadQueue(msgQueueFile);
	}
    };
}

