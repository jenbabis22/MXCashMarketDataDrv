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

package com.interacciones.mxcashmarketdata.driver.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import com.interacciones.mxcashmarketdata.driver.process.DriverServerHandler;

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

public class DriverClient extends IoHandlerAdapter {
	protected final static Log LOGGER=LogFactory.getLog(DriverClient.class);
	private static final long CONNECT_TIMEOUT = 1000L;
	private static final boolean USE_CUSTOM_CODEC = false;
	private static final int PORT = 1626;
	private static final String HOST = "localhost";

	public static void main(String[] args) throws Throwable {
	    NioSocketConnector connector = new NioSocketConnector();
	    String fileTest = "../test/Prueba.log";

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
	    for (;;) {
	        try {
	        	ConnectFuture future = connector.connect(new InetSocketAddress(HOST, PORT));
	            future.awaitUninterruptibly();
	            session = future.getSession();
	            
	            File file = new File(fileTest);
	            FileInputStream is = new FileInputStream(file);
	            InputStreamReader isr = new InputStreamReader(is);
		 	    BufferedReader br = new BufferedReader(isr);
		 	    
		 	    int data = br.read();
			    int count = 0;
			    byte[] bytes = new byte[274];
			    bytes[count] = (byte)data;
			    
			    while(data != -1){
			        data = br.read();
			        count++;
			        if (data==13){
			        	IoBuffer ib = IoBuffer.allocate(bytes.length);
			            ib.put(bytes);
			            ib.flip();
			            session.write(ib);
			            count=0;
			        	bytes = new byte[278];
			        }
			        bytes[count] = (byte)data;
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
}

