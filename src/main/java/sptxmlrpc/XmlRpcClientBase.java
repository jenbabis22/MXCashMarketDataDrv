package sptxmlrpc;

import java.net.URL;
import org.apache.xmlrpc.*;
import org.apache.xmlrpc.client.*;

/**
 * Base class for XMLRPC Clients
 * @author SPT Software
 *         Rene Barrera (rene.barrera@sptsoftware.com)
 *         Miguel Suárez (miguel.suarez@sptsoftware.com)
 *         February 2013
 */
public class XmlRpcClientBase {

    private XmlRpcClient _server;
    private String _url;

    /** Constructor con la inicialización de la instancia del servidor */
    public XmlRpcClientBase( String url ){

        if( url != null && !url.equals( "" ) )
            this._url = url;
        
        try{
            // Create an object to represent our server.
            this._server = new XmlRpcClient();
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setEnabledForExtensions( true );

            config.setServerURL( new URL( _url ) );
            _server.setConfig( config );
        }
        catch( Exception exception ) {
            System.err.println("error: " + exception.toString());
        }
    }

    /** Maneja la llamada de ejecución de un método en el servidors */
    public Object Execute( String metodo, Object[] params) {
        Object result = null;

        try {
            // Call the server, and get our result.
            result = _server.execute( metodo, params );

        } catch (XmlRpcException exception) {
            System.err.println("Error de XML-RPC #" +
                               Integer.toString(exception.code) + ": " +
                               exception.toString());
        } catch (Exception exception) {
            System.err.println("error: " + exception.toString());
        }
        
        return result;
    }
}
