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

package com.interacciones.mxcashmarketdata.driver.process;

import com.interacciones.mxcashmarketdata.driver.model.MessageRetransmission;
import com.interacciones.mxcashmarketdata.driver.process.impl.QueueMessageProcessing;
import com.interacciones.mxcashmarketdata.driver.util.CountSequence;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.ssl.SslFilter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

//This class takes in binary streams of data from the BMV, translates the character data into strings using a buffer. 

public class DriverServerHandler extends IoHandlerAdapter {
    protected final static Log LOGGER = LogFactory.getLog(DriverServerHandler.class);
    private static long myMsgSequence;
    private static CountSequence countSequence;
    private static long globlaCount;
    private static boolean flagRetransmission = true;
    private static StringBuffer strbuf = null;

    private static int MSG_LENGTH = 250;
    private static int MSG_SEQUENCE = 243;

    //private MessageProcessing messageProcessing = new FileMessageProcessing();
    private MessageProcessing messageProcessing = new QueueMessageProcessing();

    public DriverServerHandler() {
        //Singleton
        countSequence = CountSequence.getInstance();

        messageProcessing.init();

    }

    @Override
    public void sessionCreated(IoSession session) {
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        // We're going to use SSL negotiation notification.
        session.setAttribute(SslFilter.USE_NOTIFICATION);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        messageProcessing.close();
        LOGGER.info("CLOSED");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        flagRetransmission = true;
        countSequence.setValue(countSequence.getValue() - 1);
        myMsgSequence = countSequence.next();
        LOGGER.info("Sequence :" + myMsgSequence);

        strbuf = new StringBuffer();
        globlaCount = 0;

        LOGGER.info("OPENED");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        LOGGER.debug("*** IDLE #" + session.getIdleCount(IdleStatus.BOTH_IDLE) + " ***");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        session.close(true);
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        LOGGER.debug("Received : " + message);
        IoBuffer ib = (IoBuffer) message;
        try {
            InputStream is = ib.asInputStream();
            /**
             * We have to create them b/c they are needed as arguments in the constructors.
             */
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            long time = System.currentTimeMillis();
            int data = br.read();
            int count = 1;
            
            while (data != -1) {
                char theChar = (char) data;
                strbuf.append(theChar);
                count++;
                
                if (data == 13) {
                    count = 0;
                    long tmpSequence = 0;
                    String strSequence;

                    strbuf.deleteCharAt(strbuf.length() - 1);
                    int sizestrbuf = strbuf.length();
                    try {
                        //Message reader, Sequence number
                        strSequence = strbuf.substring(sizestrbuf - MSG_LENGTH, sizestrbuf - MSG_SEQUENCE);
                        tmpSequence = Long.parseLong(strSequence);

                        if (myMsgSequence == tmpSequence) {
                            myMsgSequence = countSequence.next();
                            /**
                             * Sending message File, OpenMama, Queuing
                             * Implement Interface MessageProcessing
                             */
                            messageProcessing.receive(strbuf.toString().substring(sizestrbuf - MSG_LENGTH, sizestrbuf));

                            flagRetransmission = true;
                        } else if (flagRetransmission) {
                            MessageRetransmission msgRetra = new MessageRetransmission();
                            msgRetra.setSequencelong(myMsgSequence);
                            msgRetra.MsgConstruct();
                            IoBuffer io = IoBuffer.wrap(msgRetra.getByte());
                            LOGGER.info("Sequence Retransmission " + myMsgSequence);
                            LOGGER.debug(msgRetra.toString());
                            session.write(io.duplicate());
                            flagRetransmission = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("Error: " + e.getMessage());
                        LOGGER.error(strbuf.toString());
                    }
                    strbuf.delete(0, strbuf.length()); //What's more expensive? delete or read the object.
                }
                data = br.read();
            }
            time = System.currentTimeMillis() - time;
            globlaCount = globlaCount + time;
            isr.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

