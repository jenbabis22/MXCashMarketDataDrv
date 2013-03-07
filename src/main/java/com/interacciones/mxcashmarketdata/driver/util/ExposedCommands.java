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

import com.interacciones.mxcashmarketdata.driver.process.DriverServerHandler;
import com.interacciones.mxcashmarketdata.driver.server.DriverServer;
import com.interacciones.mxcashmarketdata.mama.queue.SendMessageToMama;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class containing methods exposed in the XMLRPC server
 * @author SPT Software
 *         Rene Barrera (rene.barrera@sptsoftware.com)
 *         Miguel Suárez (miguel.suarez@sptsoftware.com)
 *         February 2013
 */
public class ExposedCommands {

    private static final SimpleDateFormat _dfTS = new SimpleDateFormat( "[dd.MM.yy hh:mm:ss.SSS] " );
    private static final SimpleDateFormat _dfDia = new SimpleDateFormat( "dd/MM/yy" );
    private static final Calendar _calendario = Calendar.getInstance();

    public ExposedCommands() { }

    /**
     * Establece el valor de secuencia para los mensajes que se reciben
     * @param newSeq Nuevo valor de secuencia
     * @return Resultado de la operación.
     */
    public String setSequence( long newSeq ){
        String resp = "OK";
        TimeStamp( "setSecuencia" );
        
        if( newSeq > 0 )
            DriverServerHandler.setSequence( newSeq );
        else
            resp = "El valor de secuencia no es válido";

        return resp;
    }

    /**
     * Pausa la publicación en todos los tópicos
     * @return Resultado de la operación
     */
    public String stopPublication( ){
        String resp = "OK";
        TimeStamp( "stopPublicacion" );

        try{
            Parameters.setPublish( false );
        }
        catch(Exception ex){
            resp = "Error al intentar detener la publicacion";
        }

        return resp;
    }

    /**
     * Inicia la publicación en todos los tópicos
     * @return Resultado de la operación
     */
    public String startPublication( ){
        String resp = "";
        TimeStamp( "startPublicacion" );

        try{
            Parameters.setPublish( true);
        }
        catch(Exception ex){
            resp = "Error al intentar iniciar la publicacion";
        }


        return resp;
    }

    /**
     * Cambia el día de la última ejecución del driver. Si es diferente de hoy, la secuencia se reinicia.
     * @param fecha nueva fecha
     * @return Resultado de la operación
     */
    public String setDate( String fecha ){
        String resp = "OK";
        TimeStamp( "setDia" );
        String fechaHoy;

        try{
            _dfDia.parse( fecha );
            fechaHoy = _dfDia.format( Calendar.getInstance().getTime() );

            if( !fechaHoy.equals( fecha ) )
                setSequence( 1 );
        }
        catch(ParseException pEx){
            resp = "Valor de fecha invalido";
        }

        return resp;
    }

    /**
     * Permite que el socket reciba mensajes
     * @return Resultado de la operacion
     */
    public String startSocket( ){
        String resp = "OK";
        TimeStamp( "startSocket" );
        try{
            DriverServerHandler.switchSocket( true );
        }
        catch(Exception ex){
            resp = "Error al intentar arrancar el socket";
        }

        return resp;
    }

    /**
     * Detiene la recepción de mensajes en el socket
     * @return Resultado de la operacion
     */
    public String stopSocket( ){
        String resp = "OK";
        TimeStamp( "stopSocket" );
        try{
            DriverServerHandler.switchSocket( false );
        }
        catch(Exception ex){
            resp = "Error al intentar detener el socket";
        }
        
        return resp;
    }

    /**
     * Establece un nuevo valor para el puerto de escucha del socket.
     * @param puerto Nuevo valor del puerto
     * @return Resultado de la operación
     */
    public String setPort( int puerto ){
        String resp = "OK";
        TimeStamp( "setPuerto" );

        if( puerto > 0 && puerto <= 65535 ){
            try{
                DriverServer.setPort( puerto );
                DriverServer.ResetConnection();
            }
            catch( IOException iOEx ){
                System.err.println( "error: " + iOEx );
                resp = "Error al intentar cambiar el puerto";
            }
        }
        else{
            resp = "Valor de puerto invalido";
        }

        return resp;
    }

    /**
     * Cambia el valor del tópico Normalizado
     * @param topico Nomnbre del tópico
     * @return Resultado de la operación.
     */
    public String setNormalizedTopic( String topico ){
        String resp = "OK";
        TimeStamp( "setTopicoNormalizado" );

        if( !topico.equals( "" ) )
            Parameters.setParam( "TopicoNormalizado", topico );
        else
            Parameters.removeParam( "TopicoNormalizado" );

        SendMessageToMama.ResetTopicos();
        Parameters.SaveParams();

        return resp;
    }

    /**
     * Cambia el valor del tópico Setrib (sin normalizar)
     * @param topico Nomnbre del tópico
     * @return Resultado de la operación.
     */
    public String setSetribTopic( String topico ){
        String resp = "OK";
        TimeStamp( "setTopicoSetrib" );

        if( !topico.equals( "" ) )
            Parameters.setParam( "TopicoSetrib", topico );
        else
            Parameters.removeParam( "TopicoSetrib" );

        SendMessageToMama.ResetTopicos();
        Parameters.SaveParams();

        return resp;
    }

    public String messageFilter( String tipoMensaje, boolean pasa ){
        String resp = "OK";

        TimeStamp( "filtraTipoMensaje ( " + tipoMensaje + ", " +
                ( pasa? "Se publica" : "No se publica" )
                + " )" );

        if( MessageType.O.getIdType().equals( tipoMensaje ) )
            MessageType.O.setPublishable( pasa );
        else if( MessageType.DO.getIdType().equals( tipoMensaje ) )
            MessageType.DO.setPublishable( pasa );
        else if( MessageType.K.getIdType().equals( tipoMensaje ) )
            MessageType.K.setPublishable( pasa );
        else if( MessageType.P.getIdType().equals( tipoMensaje ) )
            MessageType.P.setPublishable( pasa );
        
        return resp;
    }

    /**
     * Cambia el valor de secuencia y fuerza una retransmisión
     * @param secuencia Valor de secuencia deseado
     * @return Resultado de la operación
     */
    public String retransmission( long secuencia ){
        String resp = "OK";
        TimeStamp( "retransmite" );

        try{
            DriverServerHandler.forceRetrans( secuencia );
        }
        catch(Exception ex){
            resp = "Error al intentar forzar la retransmision";
        }

        return resp;
    }

    /**
     * Establece si se debe realizar validación de CheckSum
     * @param valida True si se debe validar.
     * @return Resultado de la operación
     */
    public String setCSValidation( boolean valida ) {
        String resp = "OK";
        TimeStamp( "setValidaCS" );
        
        try{
            Parameters.setValidaCS( valida );
        }
        catch(Exception ex){
            resp = "Error al cambiar la validacion de CheckSum";
        }

        return resp;
    }

    /**
     * Elimina las entradas del log
     * @return Resultado de la operación
     */
    public String cleanLog( ){
        String resp = "Esta operacion no ha sido implementada todavia";
        TimeStamp( "cleanLog" );
        return resp;
    }

    /**
     * Establece el nivel de logueo
     * @param level Nivel de logueo deseado
     * @return Resultado de la operación
     */
    public String setLoggingLevel( String level ) {
        String resp = "Esta operacion no ha sido implementada todavia";
        TimeStamp( "setLoggingLevel" );
        return resp;
    }

    /**
     * Obtiene el estatus del servidor (Secuencia y Puerto del socket)
     * @return Secuencia|Puerto
     */
    public String getStatus( ) {
        String resp = "";
        TimeStamp( "getEstatus" );

        resp = String.valueOf( DriverServer.getPort() ) + "|" + String.valueOf( DriverServerHandler.getSequence() );

        return resp;
    }

    private void TimeStamp( String metodo ){
        System.out.print( _dfTS.format( _calendario.getTime() ) );
        System.out.println( "Command execution request: " + metodo );
    }
}
