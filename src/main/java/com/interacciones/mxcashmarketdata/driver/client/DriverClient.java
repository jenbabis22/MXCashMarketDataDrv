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

package com.interacciones.mxcashmarketdata.driver.client;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.sumup.ClientSessionHandler;
import org.apache.mina.example.sumup.codec.SumUpProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class DriverClient extends IoHandlerAdapter {
    protected final static Log LOGGER = LogFactory.getLog(DriverClient.class);
    private static final long CONNECT_TIMEOUT = 1000L;
    private static final boolean USE_CUSTOM_CODEC = false;
    // Parameters default port, host, fileTest
    private static int port = 1626;
    private static String host = "localhost";
    private static String fileTest = "Prueba.log";

    public static void main(String[] args) throws Throwable {
    	init(args);
    	
        NioSocketConnector connector = new NioSocketConnector();
        
        // Configure the service.
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
        if (USE_CUSTOM_CODEC) {
            connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new SumUpProtocolCodecFactory(false)));
        } else {
            connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        }
        int[] values = new int[]{};
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.setHandler(new ClientSessionHandler(values));

        long time = System.currentTimeMillis();
        IoSession session;
        for (; ; ) {
            try {
            	System.out.println(host + " " + port + " " + fileTest);
                ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
                future.awaitUninterruptibly();
                session = future.getSession();

                File file = new File(fileTest);
                FileInputStream is = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                int data = br.read();
                int count = 0;
               
                IoBuffer ib = IoBuffer.allocate(274);
			    ib.setAutoExpand(true);
			    boolean flagcount = false;
			    
			    while(data != -1){
			    	data = br.read();
			    	ib.put((byte)data);
			    	
			        if (flagcount){count++;}
			        if (data==13){
			            count=1;
			            flagcount = true;
				    	LOGGER.debug(ib.toString());
			        }
			        if (count == 4) {
						ib.flip();
			        	session.write(ib);
			        	ib = IoBuffer.allocate(274);
			        	ib.setAutoExpand(true);
						flagcount = false;
						count = 0;
						//Thread.sleep(500);
					}
			    }

                break;
            } catch (RuntimeIoException e) {
                LOGGER.error("Failed to connect.");
                e.printStackTrace();
                Thread.sleep(5000);
            }
        }
        time = System.currentTimeMillis() - time;
        LOGGER.info("Time " + time);
        // wait until the summation is done
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
    
    private static void init(String[] args){
		if(args.length <= 0){
			LOGGER.info("Default Parameters");
		}else {
			for (int i = 0; i < args.length;) {
				String arg = args[i];
				
				if(arg.equals("-src")){
					fileTest = args[i+1];
					i+=2;
				}else if(arg.equals("-host")){
					host = args[i+1];
					i+=2;
				}else if(arg.equals("-port")){
					port = new Integer(args[i+1]);
					i+=2;
				}else{
					i++;
				}
			}
		}
	}
}

