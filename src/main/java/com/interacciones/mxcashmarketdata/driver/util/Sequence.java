// $Id$
// MXCashMarketDataDrv - An OpenMama based driver for the Mexican Cash Market Binary Feed
// An SPT Software Contribution
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Handles persistance of message sequence
 * @author SPT Software
 *         Rene Barrera (rene.barrera@sptsoftware.com)
 *         Miguel Suárez (miguel.suarez@sptsoftware.com)
 *         February 2013
 */
public class Sequence {

    private static long _sequence = 1;
    private final static String _sequenceKey = "secuencia";
    private final static String _dateKey = "fechaSeq";
    private final static String _waitKey = "esperaPersistencia";
    private final static DateFormat _dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
    private final static String _configFileSeq  = "mxcashmarketdata.sequence";
    protected final static Logger _LOGGER = Logger.getLogger( "LogGeneral" );
    private final static Calendar _fecha = Calendar.getInstance();

    private static Properties _propSeq;

    static { init(); }

    /** Handles Class intialization */
    private static void init(){
        InputStream isIni = null, isSeq = null;
        String parametro, valor;
        String _dateSeq = "", _seqParam = "";

        try{
            _propSeq = new Properties();

            //If the file does not exist, it is created
            File archivoConfig = new File( _configFileSeq );
            if( !archivoConfig.exists() )
                archivoConfig.createNewFile();

            isSeq = new FileInputStream( _configFileSeq ); _propSeq.load( isSeq );

            System.out.println("Sequence Values:");
            if( _propSeq.keys().hasMoreElements() ){
                for( Enumeration e = _propSeq.keys(); e.hasMoreElements(); ) {
                    parametro = e.nextElement().toString();
                    valor = _propSeq.getProperty( parametro );

                    if( parametro.equals( _sequenceKey ) ){
                       System.out.println("   " + parametro + "=" + valor);
                       _seqParam = valor;
                    }
                    else if( parametro.equals( _dateKey ) )
                    {
                        System.out.println("   " + parametro + "=" + valor);
                        _dateSeq = valor;
                    }
                }

                String today = _dateFormat.format( Calendar.getInstance().getTime() );

                if( today.equals( _dateSeq ) )
                    _sequence = Long.parseLong( _seqParam );
                else
                    System.out.println( "Sequence value has been reset." );
            }
            else
                System.out.println( "   -No values for sequence were found" );

            System.out.println( "" );
        } catch( IOException ex ) {
            _LOGGER.error( "Error al intentar cargar los parametros. Archivo: " + _configFileSeq + "\nDetalle: " + ex.getMessage() );
        }
        finally{
            try{
                isIni.close(); isSeq.close();
            }catch(Exception ex){}
        }
    }

    /** Stores the reported sequence value */
    public static synchronized void setSequence( long sec ){
            _sequence = sec + 1;
    }

    /** Devuelve el último valor reportado de secuencia */
    public static long getSequence(){
        return _sequence;
    }

    /** Maneja la llamada para persistir el valor de la secuencia */
    public static void PersistSeq(){
        long espera = 5000;
        String waitParam = Parameters.getParam( _waitKey );

        if( waitParam != null )
            espera = Long.parseLong( Parameters.getParam( _waitKey ) );
        else
            System.out.println( "Se toma valor default (5s) de espera para Persistencia de secuencia" );
        
        try{
            while( true ){
                SaveSequence( _sequence );
                Thread.sleep( espera );
            }
        }
        catch(Exception ex){
            System.out.println( "Ultimo valor de secuencia: " + _sequence );
        }
    }

    /**
     * Graba en el archivo properties los últimos valores de secuencia
     * @param sequence Último valor de secuencia
     */
    public static void SaveSequence( long sequence ){
        try{
            _propSeq.setProperty( _sequenceKey, String.valueOf( sequence ) );
            _propSeq.setProperty( _dateKey, _dateFormat.format( _fecha.getTime() ) );
            _propSeq.store( new FileOutputStream( _configFileSeq ), null );
        } catch (IOException ex) {
            _LOGGER.error( "Error al intentar guardar los datos de secuencia. \nDetalle: " + ex.getMessage() );
        }
    }
}
