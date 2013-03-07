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

import com.interacciones.mxcashmarketdata.driver.process.DriverServerHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DriverServer {
    protected final static Log LOGGER = LogFactory.getLog(DriverServer.class);
    private static int PORT = 1626;
    private static long _SEQUENCE = 1, _COTARX = 0;
    private static IoAcceptor _acceptor;

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        init(args);
        _acceptor = new NioSocketAcceptor();

        _acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        //acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));

        _acceptor.setHandler(new DriverServerHandler(  ));

        _acceptor.getSessionConfig().setReadBufferSize(65536);
        _acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        _acceptor.bind(new InetSocketAddress(PORT));
        LOGGER.info("Mina Server Socket Initiated");
        System.out.println("--> Version 1.0.1  Build date: 23/01/2013 <--\n" );
    }

    private static void init(String[] args) {
        if (args.length <= 0) {
            LOGGER.info("No parameters set. Taking default values for MinaSocket");
        } else {
            if (args[0].equalsIgnoreCase("-port_mina")) {
                PORT = Integer.parseInt(args[1]);
            }
        }
    }

    /**
     * Cierra el socket actual y crea uno nuevo con en mismo handler
     * @throws IOException
     */
    public static void ResetConnection() throws IOException{
        IoAcceptor newAcceptor = new NioSocketAcceptor();
        
        newAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
        newAcceptor.setHandler( _acceptor.getHandler() );
        _acceptor.dispose();
        newAcceptor.getSessionConfig().setReadBufferSize(65536);
        newAcceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        newAcceptor.bind( new InetSocketAddress( PORT ) );
        LOGGER.info("Mina Server Socket ReInitiated");
    }

    /**
     * Establece un nuevo valor para el puerto. Se toma para la siguiente inicializacion del socket.
     * @param port Valor del puerto
     */
    public static void setPort(int port){
        PORT = port;
    }

    /**
     * Obtiene el valor para el puerto.
     * @return Valor del puerto
     */
    public static int getPort(){
        return PORT;
    }
}
