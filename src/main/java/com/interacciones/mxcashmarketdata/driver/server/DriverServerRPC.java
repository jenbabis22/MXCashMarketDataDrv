// $Id$
// MXCashMarketDataDrv - An OpenMama based driver for the Mexican Cash Market Binary Feed
// SPT Software contribution
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

import com.interacciones.mxcashmarketdata.driver.util.ExposedCommands;

/**
 * XMLRPC server class for driver services
 * @author SPT Software
 *         Rene Barrera (rene.barrera@sptsoftware.com)
 *         Miguel Suárez (miguel.suarez@sptsoftware.com)
 *         February 2013
 */
public class DriverServerRPC {
    private static int _port = 8081;
    private static String [] _args;
    private static boolean _isDummy = false;

    public DriverServerRPC() { }

    /**
     * Starts XMLRPC service.
     * @param args Listening port (default:8081)
     */
    public static void main( String [] args ){
        try {
            if( args.length > 0 ){
                String arg;
                for( int i = 0; i < args.length; i++ ) {
                    arg = args[i];

                    if ( arg.equalsIgnoreCase("-portRPC")) {
                        _port = Integer.parseInt( args[ ++i ] );
                    }
                    else if ( arg.equalsIgnoreCase("-dummy" ) ) {
                        _isDummy = true;
                    }
                }
            }

            // Invoke me as <http://localhost:_port/>.
            org.apache.xmlrpc.webserver.WebServer webServer = new org.apache.xmlrpc.webserver.WebServer( _port );
            org.apache.xmlrpc.server.XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

            org.apache.xmlrpc.server.PropertyHandlerMapping phm = new org.apache.xmlrpc.server.PropertyHandlerMapping();
            phm.addHandler( "driver", ExposedCommands.class );
            
            xmlRpcServer.setHandlerMapping(phm);

            org.apache.xmlrpc.server.XmlRpcServerConfigImpl serverConfig = ( org.apache.xmlrpc.server.XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            serverConfig.setEnabledForExtensions(true);
            serverConfig.setContentLengthOptional(false);

            System.out.println( "Trying to start RPC server on port (" + _port + ") ..." );
            webServer.start();
            System.out.println( "RPC Server started.\n" );

            //Starting socket
            _args = args;
            System.out.println( "Starting Socket..." );
            Thread driver = new Thread( ArrancaDriver );
            driver.start();
            System.out.println( "Socket Started\n" );
            
        } catch( Exception exception ) {
            System.err.println( "error: " + exception.toString() );
        }
    }

    /** Instance for the method handling sequence persistance in mxcashmarket.sequence file */
    private static Runnable ArrancaDriver = new Runnable() {
        public void run() {
            try{
                DriverServer.main( _args );
            }
            catch( Exception ex ){
                System.err.println( "Error starting Driver: " + ex.toString() );
            }
        }
    };
}
