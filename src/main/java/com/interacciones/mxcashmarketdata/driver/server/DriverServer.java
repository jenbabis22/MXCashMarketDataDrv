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

package com.interacciones.mxcashmarketdata.driver.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.interacciones.mxcashmarketdata.driver.process.DriverServerHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class DriverServer {
	protected final static Log LOGGER=LogFactory.getLog(DriverServer.class);
	private static int PORT = 1626;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		init(args);
		IoAcceptor acceptor = new NioSocketAcceptor();


		acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        //acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));

        acceptor.setHandler(  new DriverServerHandler() );

        acceptor.getSessionConfig().setReadBufferSize( 65536 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        acceptor.bind( new InetSocketAddress(PORT) );
        LOGGER.info("Mina Server Socket Initiated");
	}
	
	private static void init(String[] args){
		if(args.length <= 0){
			LOGGER.info("No se setearon parametros, Tomando valores por default para MinaSocket");
		}else {
			if(args[0].equalsIgnoreCase("-port_mina")){
				PORT = Integer.parseInt(args[1]);
			}
		}
	}
}

