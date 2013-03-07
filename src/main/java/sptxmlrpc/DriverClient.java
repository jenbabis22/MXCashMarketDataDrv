package sptxmlrpc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import jline.console.ConsoleReader;
import jline.console.completer.StringsCompleter;
import jline.console.completer.ArgumentCompleter;

/**
 * Client class for XMLRPC calls, using jLine
 * @author SPT Software
 *         Rene Barrera (rene.barrera@sptsoftware.com)
 *         Miguel Suárez (miguel.suarez@sptsoftware.com)
 *         February 2013
 */
public class DriverClient {

    // Default server URL
    private String _server_url = "http://127.0.0.1:8081/";
    private String _respuesta = "";
    private static final String _promptMessage = "\nDriverCMD> ";
    private static final SimpleDateFormat _df = new SimpleDateFormat( "dd/MM/yyyy" );
    private XmlRpcClientBase _servidor;
    private ConsoleReader _reader;
    private String [] _commandList = new String[] { "setSequence", "stopPublication", "startPublication", "setDate", "stopSocket",
                                                    "startSocket", "setPort", "setNormalizedTopic", "setSetribTopic", "messageFilter",
                                                    "retransmission", "setCSValidation", "cleanLog", "setLoggingLevel", "status",
                                                    "quit", "help" };

    /** Client initialization */
    public void Init( String url ) {

        if( url != null && !url.equals( "" ) )
            _server_url = url;

        try {
            _servidor = new XmlRpcClientBase( _server_url );

            _reader = new ConsoleReader();
            _reader.setBellEnabled( false );
            List completors = new LinkedList();

            completors.add( new StringsCompleter( _commandList ) );
            _reader.addCompleter( new ArgumentCompleter( completors ) );

        } catch (Exception exception) {
            System.err.println("error: " + exception.toString());
        }
    }

    /**
     * Starts prompt until 'exit' commando is typed
     * @throws IOException If no input information can be retreived
     */
    public void Run() throws IOException {
        String[] line, params = null;
        String command;

        printWelcomeMessage();
        while ( true ) {
            line = readLine();
            command = line[ 0 ].toUpperCase();
            if( line.length > 1 ){
                params = new String[ line.length - 1 ];
                System.arraycopy( line, 1, params, 0, line.length - 1 );
            }
            else
                params = null;

            _respuesta = "";
            if( "SETSEQUENCE".equals(command) ){
                setSequence( params );
            } else if( "STOPPUBLICATION".equals(command)) {
                stopPublication();
            } else if( "STARTPUBLICATION".equals(command)) {
                startPublication();
            } else if( "SETDATE".equals(command)) {
                setDate( params );
            } else if( "STOPSOCKET".equals(command)) {
                stopSocket();
            } else if( "STARTSOCKET".equals(command)) {
                startSocket();
            } else if( "SETPORT".equals(command)) {
                setPort( params );
            } else if( "SETNORMALIZEDTOPIC".equals(command)) {
                if( params == null || params[0] == null ) params = new String[]{""};
                setNormalizedTopic( params );
            } else if( "SETSETRIBTOPIC".equals(command)) {
                if( params == null || params[0] == null ) params = new String[]{""};
                setSetribTopic( params );
            } else if( "MESSAGEFILTER".equals(command)) {
                messageFilter( params );
            } else if( "RETRANSMISSION".equals(command)) {
                retransmission( params );
            } else if( "SETCSVALIDATION".equals(command)) {
                setCSValidation( params );
            } else if( "CLEANLOG".equals(command)) {
                cleanLog();
            } else if( "SETLOGGINGLEVEL".equals(command)) {
                setLoggingLevel( params );
            } else if( "STATUS".equals(command)) {
                getStatus( params );
            } else if( "HELP".equals( command ) || "?".equals( command ) || "/?".equals( command ) ) {
                printHelp();
            } else if( "EXIT".equals( command ) || "SALIR".equals( command ) || "QUIT".equals( command ) || "BYE".equals( command ) ) {
                System.out.println( "Bye!" );
                return;
            } else if( "test".equals( command ) ) {
                //Llamada a una función dummy para probar
                System.out.println( "TESTING..." );
                Object[] p = {5, 2};
                HashMap result = (HashMap) _servidor.Execute( "driver.sumAndDifference", p );
                int sum = ((Integer) result.get("sum")).intValue();
                int difference = ((Integer) result.get("difference")).intValue();
                // Print out our result.
                System.out.println("Sum: " + Integer.toString(sum) +
                                   ", Difference: " + Integer.toString(difference));
            }
            else
                System.out.println("Command unknown.");

            if( !_respuesta.equals( "" ) )
                System.out.println( "Server response:" + _respuesta );
            //out.flush();
        }
    }

    /** Prints the initial message for command line */
    private void printWelcomeMessage() {
            System.out.println("Control consle for OpenMAMA Driver. For help, press TAB o type \"help\" and hit ENTER.");
    }

    /** Prints all possible commands and a short description for each one */
    private void printHelp() {
            System.out.println("setSequence [sequence]                        \n     Sets the counter sequence of the driver.");
            System.out.println("stopPublication                               \n     Stops message publishing.");
            System.out.println("startPublication                              \n     Starts message publishing. No action is taken when driver is already publishing.");
            System.out.println("setDate [date]                                \n     Sets the date in 'DD/MM/YY' format");
            System.out.println("stopSocket                                    \n     Stops the socket. No effect if it is not running.");
            System.out.println("startSocket                                   \n     Starts the socket. No effect if it is already running.");
            System.out.println("setPort [port]                                \n     Sets the socket's port value to listen on");
            System.out.println("setNormalizedTopic [topic]                    \n     Sets the topic that will be published normalized. If setNormalizedTopic recieves an empty string, it will stop publishing.");
            System.out.println("setSetribTopic [topic]                        \n     Sets  the TOPIC that will be published as it comes. If setNormalizedTopic recieves an empty string, it will stop publishing.");
            System.out.println("messageFilter [messageType] [booleanPass]     \n     Defines wether a type of message [messageType] will be published or not [booleanPass]");
            System.out.println("retransmission [sequence]                     \n     Defines a sequence value [sequence] and asks for retransmition");
            System.out.println("setCSValidation [validates]                   \n     Defines wether Checksum is validated or not.");
            System.out.println("cleanLog                                      \n     Cleans log");
            System.out.println("setLoggingLevel [nivel]                       \n     Sets the level to which the application must log [FATAL/ERROR/WARN/INFO/DEBUG/TRACE/ALL]");
            System.out.println("status                                        \n     Shows service state variables");
            System.out.println("help, ?, /?                                   \n     Shows help");
            System.out.println("exit, salir, quit, bye                        \n     Ends execution of this shell");
    }

    /**
     * Reads a line from prompt
     * @param reader Console read object
     * @param promtMessage Prompt message
     * @return Received string with trimmed spaces
     * @throws IOException If the console cannot be read
     */
    private String[] readLine( ) throws IOException {
            String line = _reader.readLine( _promptMessage );
            return line.trim().split( " " );
    }

    /**
     * Handles calls for client intialization
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
            DriverClient cmd = new DriverClient();
            cmd.Init( args.length > 0 ? args[0] : "" );
            cmd.Run();
    }

    ////Server functions definition

    public void setSequence( String[] params ){
        if( params != null && params.length > 0 ){
            try{
                Long secuencia = Long.parseLong( params[0] );
                _respuesta = (String) _servidor.Execute( "driver.setSequence", new Object[] { secuencia } );
            }
            catch( Exception ex){
                System.err.println( "Error de parametros: " + ex.toString() );
            }
        }
    }

    public void stopPublication( ){
        _respuesta = (String) _servidor.Execute( "driver.stopPublication", null );
    }

    public void startPublication( ){
        _respuesta = (String) _servidor.Execute( "driver.startPublication", null );
    }

    public void setDate( String [] params ){
        if( params != null && params.length > 0 ){
            try{
                _df.parse( params[0] );
                _respuesta = (String) _servidor.Execute( "driver.setDate", params );
            }
            catch( Exception ex){
                System.err.println( "Error de parametros: " + ex.toString() );
            }
        }
    }

    public void startSocket( ){
       _respuesta = (String) _servidor.Execute( "driver.startSocket", null );
    }

    public void stopSocket( ){
        _respuesta = (String) _servidor.Execute( "driver.stopSocket", null );
    }

    public void setPort( String [] params ){
        if( params != null && params.length > 0 ){
            try{
                int puerto = Integer.parseInt( params[0] );
                _respuesta = (String) _servidor.Execute( "driver.setPort", new Object[] { puerto } );
            }
            catch( Exception ex){
                System.err.println( "Error de parametros: " + ex.toString() );
            }
        }
    }

    public void setNormalizedTopic( String[] params ){
        _respuesta = (String) _servidor.Execute( "driver.setNormalizedTopic", params );
    }

    public void setSetribTopic( String[] params ){
        _respuesta = (String) _servidor.Execute( "driver.setSetribTopic", params );
    }

    public void messageFilter( String[] params ){
        if( params != null && params.length > 1 ){
            try{
                String tipoMensaje = params[0];
                boolean pasa = Boolean.parseBoolean( params[1] );
                _respuesta = (String) _servidor.Execute( "driver.messageFilter", new Object[] { tipoMensaje, pasa } );
            }
            catch( Exception ex){
                System.err.println( "Error de parametros: " + ex.toString() );
            }
        }
    }

    public void retransmission( String[] params ){
        if( params != null && params.length > 0 ){
            try{
                Long secuencia = Long.parseLong( params[0] );
                _respuesta = (String) _servidor.Execute( "driver.retransmission", new Object[] { secuencia } );
            }
            catch( Exception ex){
                System.err.println( "Error de parametros: " + ex.toString() );
            }
        }
    }

    public void setCSValidation( String[] params ) {
        if( params != null && params.length > 0 ){
            try{
                boolean valida = Boolean.parseBoolean( params[0] );
                _respuesta = (String) _servidor.Execute( "driver.setCSValidation", new Object[] { valida } );
            }
            catch( Exception ex){
                System.err.println( "Error de parametros: " + ex.toString() );
            }
        }
    }

    public void cleanLog( ){
        _respuesta = (String) _servidor.Execute( "driver.cleanLog", null );
    }

    public void setLoggingLevel( String[] params ) {
        _respuesta = (String) _servidor.Execute( "driver.setLoggingLevel", params );
    }

    public void getStatus( String[] params ){
        String [] valores = new String[2];

        _respuesta = (String) _servidor.Execute( "driver.getStatus", params );

        try{
            valores = _respuesta.split( "\\|" );
            _respuesta = "\nServer values:\n" +
                         "      URL:          " + _server_url + "\n" +
                         "      Socket Port: " + valores[0] + "\n" +
                         "      Sequence:    " + valores[1] + "\n";
        }catch(Exception ex){
        }
    }

}
