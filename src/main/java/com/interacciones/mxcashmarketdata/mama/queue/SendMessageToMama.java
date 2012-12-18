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

import com.interacciones.mxcashmarketdata.mama.message.Parser;
import com.interacciones.mxcashmarketdata.mama.util.ListPublisher;
import com.wombat.mama.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMessageToMama {
    protected final static Log LOGGER = LogFactory.getLog(SendMessageToMama.class);
    private String midd = "avis";
    //private String topic = "MAMAS_TOPIC";
    private String topic = "TOPIC";
    private static String namePub = "pru";
    private static MamaBridge bridge = null;
    private static MamaTransport mamaTransport = null;
    private static MamaTransportTopicListener topicListener = null;
    private static MamaMsg mamaMsg = null;
    private static MamaPublisher subscrip = null;
    private static SimpleDateFormat formato = null;
    private ListPublisher listPublisher = null;

    //private static StringBuffer strbuf = null;

    public SendMessageToMama() {
        listPublisher = ListPublisher.getInstance();
        formato = new SimpleDateFormat("H:mm:ss");

        bridge = Mama.loadBridge(midd);
        LOGGER.debug("At this point the middleware configurations are loaded.");
        Mama.open();
        Mama.getDefaultQueue(bridge);
        mamaTransport = new MamaTransport();
        topicListener = new MamaTransportTopicListener();

        mamaTransport.addTransportTopicListener(topicListener);
        mamaTransport.create(namePub, bridge);
        mamaMsg = new MamaMsg();

        /**
         * Routing topic to mama
         **/
        listPublisher.setTransport(mamaTransport);
    }

    public void sendMessage(Parser message) {
	String typeMessage = message.TypeMessage().trim();
        if (typeMessage.equals("O") || typeMessage.equals("DO")) {
            msgO_OD(message);
        } else {
            //TODO
        }
    }

    private void msgO_OD(Parser message) {
        mamaMsg.clear();
        //mamaMsg.addU32("MessageNo", 10001, tmpSequence);
        String emisora = message.Emisora().trim();
        mamaMsg.addString("PublisherTopic", 10002, emisora.concat(message.Serie().concat(message.TypeValue())));
        mamaMsg.addString("QouteTimestamp", 10004, formato.format(new Date()));
        MamaPrice askPrice = new MamaPrice();
        askPrice.add(message.AskPrice());
        mamaMsg.addPrice("AskPrice", 10005, askPrice);
        MamaPrice bidPrice = new MamaPrice();
        bidPrice.add(message.BidPrice());
        mamaMsg.addPrice("BidPrice", 10006, bidPrice);
        mamaMsg.addI64("AskSize", 10007, message.AskSize());
        mamaMsg.addI64("BidSize", 10008, message.BidSize());

        /**
         * publisher for topic
         */
        subscrip = listPublisher.get(emisora);
        subscrip.send(mamaMsg);
    }

    public void close() {
        mamaTransport.destroy();
        listPublisher.clear();
        Mama.close();
    }
}

