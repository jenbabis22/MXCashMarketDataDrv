// $Id: e757339783d40678c63c007eefbee41c1b8fdaf5 $
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

import com.interacciones.mxcashmarketdata.driver.util.Parameters;
import com.interacciones.mxcashmarketdata.driver.util.Sequence;
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
    //private String topicNormal = "MAMA_TOPIC";
    private static String _topicNormal = "", _topicSetrib = "";
    private static String namePub = "pru";
    private static MamaBridge bridge = null;
    private static MamaTransport mamaTransport = null;
    private static MamaTransportTopicListener topicListener = null;
    private static MamaMsg mamaMsg = null, mamaMsgSetrib = null;
    private static MamaPublisher publisher = null;
    private static SimpleDateFormat format = null;
    private ListPublisher listPublisher = null;
    private static boolean _pubNormal = false, _pubSetrib = false;

    public SendMessageToMama() {
        listPublisher = ListPublisher.getInstance();
        format = new SimpleDateFormat("H:mm:ss");

        ResetTopicos();

        bridge = Mama.loadBridge(midd);
        LOGGER.debug("At this point the middleware configurations are loaded.");
        Mama.open();
        Mama.getDefaultQueue(bridge);
        mamaTransport = new MamaTransport();
        topicListener = new MamaTransportTopicListener();

        mamaTransport.addTransportTopicListener(topicListener);
        mamaTransport.create(namePub, bridge);
        mamaMsg = new MamaMsg();
        mamaMsgSetrib = new MamaMsg();

        /**
         * Routing topic to mama
         *
         */
        listPublisher.setTransport(mamaTransport);
    }

    public void sendMessage(Parser message) {
        String typeMessage = message.TypeMessage().trim();
        if (typeMessage.equals("O") || typeMessage.equals("DO")) {
            msgO_DO(message);
        } else if (typeMessage.equals("E")) {
            msgE(message);
        } else if (typeMessage.equals("U")) {
            msgU(message);
        } else if (typeMessage.equals("P") || typeMessage.equals("DP")) {
            msgP_DP(message);
        } else if (typeMessage.equals("K")) {
            msgK(message);
        } else if (typeMessage.equals("2")) {
            msg2(message);
        } else if (typeMessage.equals("X")) {
            msgX(message);
        } else if (typeMessage.equals("Y")) {
            msgY(message);
        } else {
            LOGGER.debug("Message type '" + typeMessage + "' DISCARDED");
            System.out.println("Message type '" + typeMessage + "' DISCARDED");
            //TODO
        }
    }
//Publish to susbcirbers.

    private void msgO_DO(Parser message) {

        if (_pubNormal) {
            mamaMsg.clear();
            //mamaMsg.addU32("MessageNo", 10001, tmpSequence);
            String emisora = message.Emisora().trim();
            mamaMsg.addString("PublisherTopic", 10002, emisora.concat(message.Serie().concat(message.TypeValue())));
            mamaMsg.addString("QouteTimestamp", 10004, format.format(new Date()));
            MamaPrice askPrice = new MamaPrice();
            askPrice.add(message.AskPrice());
            mamaMsg.addPrice("AskPrice", 10005, askPrice);
            MamaPrice bidPrice = new MamaPrice();
            bidPrice.add(message.BidPrice());
            mamaMsg.addPrice("BidPrice", 10006, bidPrice);
            mamaMsg.addI64("AskSize", 10007, message.AskSize());
            mamaMsg.addI64("BidSize", 10008, message.BidSize());


            publisher = listPublisher.get(_topicNormal);
            publisher.send(mamaMsg);
        }
        if (_pubSetrib) {
            mamaMsgSetrib.clear();
            //mamaMsgSetrib.addString( "MessageNo", 10001, message.Emisora().trim() );
            mamaMsgSetrib.addString("MessageNo", 10001, message.getCompleteMsg());

            publisher = listPublisher.get(_topicSetrib);
            publisher.send(mamaMsgSetrib);
        }
        /*
         Sequence.setSequence(message.getSequence());
         System.out.print("\rMensaje " + message.getSequence() + "         ");
         * */
    }

    private void msgE(Parser message) {
        if (_pubNormal) {
            mamaMsg.clear();
            String emisora = message.Emisora().trim();
            mamaMsg.addString("PublisherTopic", 10002, emisora.concat(message.Serie().concat(message.TypeValue())));
            mamaMsg.addString("QouteTimestamp", 10004, format.format(new Date()));

            publisher = listPublisher.get(_topicNormal);
            publisher.send(mamaMsg);
        }
        if (_pubSetrib) {
            mamaMsgSetrib.clear();
            mamaMsgSetrib.addString("MessageNo", 10001, message.getCompleteMsg());

            publisher = listPublisher.get(_topicSetrib);
            publisher.send(mamaMsgSetrib);
        }
    }

    private void msgU(Parser message) {
        if (_pubNormal) {
            mamaMsg.clear();
            String emisora = message.Emisora().trim();
            mamaMsg.addString("QouteTimestamp", 10004, format.format(new Date()));

            publisher = listPublisher.get(_topicNormal);
            publisher.send(mamaMsg);
        }
        if (_pubSetrib) {
            mamaMsgSetrib.clear();
            mamaMsgSetrib.addString("MessageNo", 10001, message.getCompleteMsg());

            publisher = listPublisher.get(_topicSetrib);
            publisher.send(mamaMsgSetrib);
        }
    }

    private void msgP_DP(Parser message) {
        if (_pubNormal) {
            mamaMsg.clear();
            String emisora = message.Emisora().trim();
            mamaMsg.addString("PublisherTopic", 10002, emisora.concat(message.Serie().concat(message.TypeValue())));
            mamaMsg.addString("QouteTimestamp", 10004, format.format(new Date()));

            publisher = listPublisher.get(_topicNormal);
            publisher.send(mamaMsg);
        }
        if (_pubSetrib) {
            mamaMsgSetrib.clear();
            mamaMsgSetrib.addString("MessageNo", 10001, message.getCompleteMsg());

            publisher = listPublisher.get(_topicSetrib);
            publisher.send(mamaMsgSetrib);
        }
    }

    private void msg2(Parser message) {
        if (_pubNormal) {
            int i = 11111;
            int i2 = 22222;
            mamaMsg.clear();
            String emisora = message.Emisora().trim();
            mamaMsg.addString("PublisherTopic", 10002, emisora.concat(message.Serie().concat(message.TypeValue())));
            mamaMsg.addString("QouteTimestamp", 10004, format.format(new Date()));
            //TAG de prueba
            mamaMsg.addString("Tag de prueba(askprice-string) ", i, Double.toString(message.AskPrice()));
            mamaMsg.addF64("Tag de prueba (askprice-F64) ", i2, message.AskPrice());



            publisher = listPublisher.get(_topicNormal);
            publisher.send(mamaMsg);
        }
        if (_pubSetrib) {
            mamaMsgSetrib.clear();
            mamaMsgSetrib.addString("MessageNo", 10001, message.getCompleteMsg());

            publisher = listPublisher.get(_topicSetrib);
            publisher.send(mamaMsgSetrib);
        }
    }

    private void msgK(Parser message) {
        if (_pubNormal) {
            mamaMsg.clear();
            String emisora = message.Emisora().trim();
            mamaMsg.addString("PublisherTopic", 10002, emisora.concat(message.Serie().concat(message.TypeValue())));
            mamaMsg.addString("QouteTimestamp", 10004, format.format(new Date()));

            publisher = listPublisher.get(_topicNormal);
            publisher.send(mamaMsg);
        }
        if (_pubSetrib) {
            mamaMsgSetrib.clear();
            mamaMsgSetrib.addString("MessageNo", 10001, message.getCompleteMsg());

            publisher = listPublisher.get(_topicSetrib);
            publisher.send(mamaMsgSetrib);
        }
    }

    private void msgX(Parser message) {
        if (_pubNormal) {
            mamaMsg.clear();
            String emisora = message.Emisora().trim();
            mamaMsg.addString("PublisherTopic", 10002, emisora.concat(message.Serie().concat(message.TypeValue())));
            mamaMsg.addString("QouteTimestamp", 10004, format.format(new Date()));

            publisher = listPublisher.get(_topicNormal);
            publisher.send(mamaMsg);
        }
        if (_pubSetrib) {
            mamaMsgSetrib.clear();
            mamaMsgSetrib.addString("MessageNo", 10001, message.getCompleteMsg());

            publisher = listPublisher.get(_topicSetrib);
            publisher.send(mamaMsgSetrib);
        }
    }

    private void msgY(Parser message) {

        if (_pubNormal) {
            mamaMsg.clear();
            String emisora = message.Emisora().trim();
            mamaMsg.addString("PublisherTopic", 10002, emisora.concat(message.Serie().concat(message.TypeValue())));
            mamaMsg.addString("QouteTimestamp", 10004, format.format(new Date()));

            publisher = listPublisher.get(_topicNormal);
            publisher.send(mamaMsg);
        }
        if (_pubSetrib) {
            mamaMsgSetrib.clear();
            mamaMsgSetrib.addString("MessageNo", 10001, message.getCompleteMsg());

            publisher = listPublisher.get(_topicSetrib);
            publisher.send(mamaMsgSetrib);
        }
    }

    public void close() {
        mamaTransport.destroy();
        listPublisher.clear();
        Mama.close();
    }

    /**
     * Loads topic names
     */
    public static synchronized void ResetTopicos() {
        //Normal publishing?
        _topicNormal = Parameters.getParam("TopicoNormalizado");
        _pubNormal = _topicNormal != null;
        if (_pubNormal) {
            System.out.println("Normalized publishing in topic: " + _topicNormal);
        } else {
            System.out.println("NO Normalized publishing");
        }

        //Setrib publishing?
        _topicSetrib = Parameters.getParam("TopicoSetrib");
        _pubSetrib = _topicSetrib != null;
        if (_pubSetrib) {
            System.out.println("Setrib publishing in topic: " + _topicSetrib);
        } else {
            System.out.println("NO Setrib publishing");
        }
    }
}
