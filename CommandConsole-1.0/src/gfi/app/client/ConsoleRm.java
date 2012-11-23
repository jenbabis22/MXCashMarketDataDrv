package gfi.app.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import jline.ArgumentCompletor;
import jline.ConsoleReader;
import jline.FileNameCompletor;
import jline.History;
import jline.SimpleCompletor;
import jline.Terminal;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class ConsoleRm implements Runnable{
	
	public ConsoleRm() {
		setupConsole();
		
		if (Terminal.getTerminal().isSupported()) {
			Thread t = new Thread(this);
			t.setName("commandLine");
			t.start();
		}else{
			logger.warn("Terminal not supported.");
		}
	}

	private static Logger logger = Logger.getLogger(ConsoleRm.class);
	
	private String host="127.0.0.1";
	private String port="8085";
	private String user="";
	private String password="";
	
	private static ConsoleReader reader; 
	private static XmlRpcClient client;
	private static HashMap<String, String> list = new HashMap<String, String>();
	
	public static void main(String[] args) throws Exception{
		
		ConsoleRm console = new ConsoleRm();
		//Validate arguments
		console.setHost(args[0]);
		console.setPort(args[1]);
		
		//validación 
		if(args.length < 4){
			logger.info("Debes introducir los argumentos: host puerto usuario password");
			System.exit(-1);
		}
	    
		//Inicializando la coneccion via Web por XML-RPC
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	    config.setServerURL(new URL("http://"+ console.getHost() +":"+ console.getPort()+"/xmlrpc"));
	    config.setEnabledForExtensions(true);
	    client = new XmlRpcClient();
	    client.setConfig(config);
	    
	    /*
	     * AQUI LE DEBE PEDIR LOS COMANDOS DISPONIBLES AL SERVIDOR
	     * LOS CUALES SE IMPRIMIRAN CON EL COMANDO USAGE
	     */
	    Vector params = new Vector();
		params.add(args);
	    String resultCommand = (String)client.execute("usage.executeCommand", params);
	    addListCommand(resultCommand);
	}
	
	public String createPrompt(){
		String prompt;
		prompt = getHost()+":"+getPort()+">";
		return prompt;
	}
	
	public void setupConsole() {
		try{
			logger.debug("Recuperendo la Historia de la Consola Remota");
			reader = new ConsoleReader();
	        reader.setBellEnabled(false);
	        reader.setDebug(new PrintWriter(new FileWriter("conosole.log", true)));
	        History history = getHistory("console.history");
	        reader.setHistory(history);
	        
			logger.debug("Inicializando La Configuración de la Consola");
			List completors = new LinkedList();
			
			completors.add(new FileNameCompletor());
			//ESTOS PARAMETROS SE CARGARAN DE UN *.properties
			//Mi idea es que sean los comandos, cuesta trabajo recordarlos todos jejeje
			completors.add(new SimpleCompletor(new String[] { "foo", "bar", "baz" }));
			reader.addCompletor(new ArgumentCompletor(completors));
		}catch (IOException e) {
			logger.error("Error: " + e.getMessage());
		}
	}

	@Override
	public void run() {
		String line=null;
		
		
		PrintWriter out = new PrintWriter(System.out);
        boolean cont = true;
        while (cont) {
            
            try{
    			line=reader.readLine(createPrompt());
    			
    			out.println("======>\"" + line + "\"");
                out.flush();
    			
                if(line!=null){  	
                	if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                        break;
                    }
                	
                	cont = execute(line, out);
                }
                // If we input the special word then we will mask
                // the next line.
                /*if ((trigger != null) && (line.compareTo(trigger) == 0)) {
                    line = reader.readLine("password> ", mask);
                }*/
                
    		}catch (Exception e) {
    			logger.error(e.getMessage());
    		}
        }
	}
	
	public boolean execute(String line, PrintWriter out) {
		if(empty(line)){
			return true;
		}
		logger.debug(line);
		//Obteniendo los argumentos de la linea de comandos
		String args[] = line.split(" ");
		String command = args[0].trim();
		logger.info(command);

		/**
		 * Validando si existe el comando
		 */
		if(list.get(command) == null){
			logger.info("No existe el comando, teclea usage para los comandos disponibles");
			out.printf("","======>\" El Comando no se Encuentra Registrado: " + command + "\"");
            out.flush();
			return true;
		}
		
			
		//Object[] params = new Object[]{new Object[]{args}};
		Vector params = new Vector();
		params.add(args);
		try{
		    String resultadd = (String) client.execute(command.concat(".executeCommand"), params);
		    logger.info("Resultado: " + resultadd);
		    out.println("======>\"" + resultadd + "\"");
            out.flush();
		}catch (XmlRpcException e) {
		    e.printStackTrace();
			logger.error(e.getMessage());
		}
		return true;
	}
	
	public boolean empty(String line){
		line = line.trim();
		return line.isEmpty();
	}
	
	public static void addListCommand(String result){
		/**
	     * Cambiarlo por JSON, ahorita es rapido y cochino 
	     */
	    String[] objects = result.split("@");
	    for (int i = 0; i < objects.length; i++) {
			String[] tmp = objects[i].split("\\|");
			list.put(tmp[0], tmp[1]);
		}
	}
	
	public static History getHistory(String fileHistory){
		File historyFile = new File(fileHistory);
		
		History history = null;
		try {
			history = new History(historyFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  history;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
