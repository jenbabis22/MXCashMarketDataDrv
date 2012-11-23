package gfi.app.manager;

import java.io.IOException;

import org.apache.xmlrpc.XmlRpcException;

import gfi.app.command.CommandRemote;
import gfi.app.command.impl.RecoveryCommand;
import gfi.app.command.impl.StopProccessCommand;
import gfi.app.command.impl.UsageCommand;
import gfi.app.server.ServerWebManager;

public class CommandManager extends CommandManagerInvokerImpl {
	private ServerWebManager server;
	
	/**
	 * Se iniciaran los servicios tanto de los comandos
	 * como del App Web de XML-RPC
	 */
	public CommandManager() {
		startService();
	}
	
	private void startService(){
		//Iniciando WebService
		try{
			server = new ServerWebManager();
			server.run();
			//CommandInvoker invoker = CommandInvoker.getInstance();
			//invoker.setHandlerMapping(server.getHandlerMapping());
			//invoker.addCommandDefault();
			this.initInvoke(server.getHandlerMapping());
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}catch (XmlRpcException e) {
			System.out.println(e.getMessage());
		}
	}
}
