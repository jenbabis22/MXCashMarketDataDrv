package gfi.app.server;

import java.io.IOException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class ServerWebManager {
	//private static ServerWebManager instance;
	
	private PropertyHandlerMapping handlerMapping;
	private ResourceBundle res;
	private WebServer webServer;
	
	private static int port = 8085; //port default
	private boolean keepAlive;
	
	public ServerWebManager() throws IOException, XmlRpcException {
		this.loadConf();
		webServer = new WebServer(port);
		handlerMapping = new PropertyHandlerMapping();
		//System.out.println(webServer.getPort());
		this.setHandlerMapping(new PropertyHandlerMapping());
		this.setUp();
	}
	
	private void loadConf(){
		try{
			res = ResourceBundle.getBundle("webxml");
			
			Enumeration<String> confweb = res.getKeys();
			StringBuffer key = new StringBuffer();
			
			while (confweb.hasMoreElements()) {
				String confvar = confweb.nextElement();
				System.out.println(confvar);
				String confvalue = res.getString(confvar);
				
				if(confvar.equalsIgnoreCase("port")){
					port = new Integer(confvalue).intValue();
					System.out.println(port);
				}else if(confvar.equalsIgnoreCase("keepAlive")){
					this.setKeepAlive(confvalue);
				}
			}
		}catch (MissingResourceException e) {
			System.out.println(e.getMessage());
			System.out.println("Parametros por default para ServidorWeb Remoto");
		}
	}
	
	public void setUp(){
		XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
		XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
		xmlRpcServer.setHandlerMapping(handlerMapping);
		serverConfig.setEnabledForExtensions(true);
        serverConfig.setContentLengthOptional(false);
        serverConfig.setKeepAliveEnabled(true);
	}
	
	public void run(){
		//Se metera la carga de los comandos
		try {
			webServer.start();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public PropertyHandlerMapping getHandlerMapping() {
		return handlerMapping;
	}

	public void setHandlerMapping(PropertyHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	public void setKeepAlive(String keepAlive) {
		this.keepAlive = new Boolean(keepAlive).booleanValue();
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}
}
