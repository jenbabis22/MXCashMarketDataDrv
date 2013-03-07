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
package com.interacciones.mxcashmarketdata.driver.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;

/**
 * Handles application parameters
 * @author SPT Software
 *         Rene Barrera (rene.barrera@sptsoftware.com)
 *         Miguel Suárez (miguel.suarez@sptsoftware.com)
 *         February 2013
 */
public class Parameters {

    private final static Map<String, String> _parametros = new HashMap<String, String>();
    //private final static String _configFileLog = "classes/log4j.properties";
    private final static String _configFileInit = "mxcashmarketdata.properties";
    //protected final static Logger _LOGGER = Logger.getRootLogger();
    protected final static Logger _LOGGER = Logger.getLogger( "LogGeneral" );
    //protected final static Log _LOGGER = LogFactory.getLog( Parametros.class );
    protected final static Logger _logMensajes = Logger.getLogger( "MensajesRecibidos" );
    //protected final static Logger _logMensajes = LogFactory.getLog( "MensajesRecibidos" );
    private static Properties _propIni;

    /*VAriables de filtrado*/
    private static boolean _publicando = true;
    private static boolean _validaCS = true;

    static { init(); }

    private static void init(){
        //PropertyConfigurator.configure( _configFileLog );
        InputStream isIni = null, isSeq = null;
        String parametro, valor;

        try {
            _propIni = new Properties();
            isIni = new FileInputStream( _configFileInit ); _propIni.load(isIni);
            
            System.out.println( "Parametros de arranque:" );
            for( Enumeration e = _propIni.keys(); e.hasMoreElements(); ) {
                parametro = e.nextElement().toString();
                valor = _propIni.getProperty( parametro );
                _parametros.put(parametro, valor);
                System.out.println("   " + parametro + "=" + valor);
            }
            System.out.println( "" );
        } catch( IOException ex ) {
            _LOGGER.error( "Error al intentar cargar los parametros. Archivo: " + _configFileInit + "\nDetalle: " + ex.getMessage() );
        }
        finally{
            try{
                isIni.close(); isSeq.close();
            }catch(Exception ex){}
        }
    }

    /**
     * Obtiene el valor de un parámetro
     * @param key Nombre del parámetro
     * @return Valor en cadena
     */
    public static String getParam(String key) {
        String param = _parametros.get( key );
        if( param != null )
            param = param.trim();
        return param;
    }

    /**
     * establece el valor de un parámetro
     * @param key Nombre del parámetro a modificar
     * @return Cadena con el nuevo valor del parámetro
     */
    public static void setParam(String key, String val) {
        _parametros.put( key, val );
    }

    /**
     * elimina un parámetro
     * @param key Nombre del parámetro a modificar
     */
    public static void removeParam(String key ) {
        _parametros.remove( key );
    }

    /**
     * Persists the last values set for parameters in the properties file
     */
    public static void SaveParams( ){
        try{
            for( Map.Entry<String, String> param : _parametros.entrySet() )
                _propIni.setProperty( param.getKey(), param.getValue() );
            
            _propIni.store( new FileOutputStream( _configFileInit ), null );
        } catch (IOException ex) {
            _LOGGER.error( "Error when trying to save params in .properties file. \nDetail: " + ex.getMessage() );
        }
    }

    /**
     * Loguea un mensaje recibido
     * @param msg Cadena con el mensaje
     */
    public static void LogMensaje( String msg ){
        _logMensajes.info( msg );
    }

    /** Devuelve el objeto para logueo */
    public static Logger LOG( ){
        return _LOGGER;
    }

    /** Devuelve True si se puede publicar. */
    public synchronized static boolean Publish( ){
        return _publicando;
    }

    /**
     * Establece el valor para publicación
     * @param publish True si se puede publicar. False para detener la publicación.
     */
    public synchronized static void setPublish( boolean publish ){
        _publicando = publish;
    }

    /** Devuelve True si se debe validar. */
    public synchronized static boolean ValidaCS( ){
        return _validaCS;
    }

    /**
     * Establece el valor para validación de CheckSum
     * @param publish True si se debe validar.
     */
    public synchronized static void setValidaCS( boolean valida ){
        _validaCS = valida;
    }
}
